package org.safin.yandex.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ShopUnitService {

    private final ShopUnitRepository repository;
    private final ShopUnitImportRequestValidator validator;

    public static final Pattern uuidPattern = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");

    @Autowired
    public ShopUnitService(ShopUnitRepository repository, ShopUnitImportRequestValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    /**
     * delete shopUnit from database including deleting children recursively and updating parents cost and countOffers
     * @param id id to delete
     * @return if deleting went successful
     */
    public boolean deleteShopUnit(String id) {
        Optional<ShopUnit> optUnit = repository.findById(id);
        if (optUnit.isEmpty()) {
            return false;
        }
        ShopUnit unit = optUnit.get();
        repository.deleteUnitWithChildren(unit);
        if (unit.getParentId() != null) {
            changeCostToParents(unit.getParentId(), -unit.getCost(), -unit.getOffersCount());
        }
        return true;
    }

    /**
     * adding/updating units from request's list of imports with changing parents costs, countOffers and date
     * @return is request valid
     */
    public boolean addUnitFromRequest(ShopUnitImportRequest importRequest) {
        if (!validator.isValid(importRequest)) {
            return false;
        }
        List<ShopUnit> treeRoots = formTrees(importRequest);
        treeRoots.forEach(x -> {
            calculateTreePrices(x);
            cutExistingUnits(x);
            addTreeToRepository(x);
        });
        return true;
    }

    /**
     * delete this unit and its children recursively from database with updating parents costs and countOffers
     * @param unit this unit
     */
    private void cutExistingUnits(ShopUnit unit) {
        if (unit.getType() == ShopUnitType.CATEGORY && unit.getOffersCount() != 0) {
            for (ShopUnit child : unit.getChildren()) {
                cutExistingUnits(child);
            }
        }
        cutExistingUnit(unit);
    }
    /**
     * delete this unit(without children) from database with updating parents costs and countOffers
     * @param unit this unit
     */
    private void cutExistingUnit(ShopUnit unit) {
        Optional<ShopUnit> optActualUnit = repository.findById(unit.getId());
        if (optActualUnit.isEmpty()) {
            return;
        }
        ShopUnit actualUnit = optActualUnit.get();
        if (actualUnit.getParentId() != null) {
            Optional<ShopUnit> optParent = repository.findById(actualUnit.getParentId());
            if (optParent.isEmpty()) {
                throw new IllegalArgumentException("There is no such parent");
            }
            ShopUnit parent = optParent.get();
            parent.deleteChild(actualUnit);
            if (parent.getParentId() != null) {
                changeCostToParents(parent.getParentId(), -actualUnit.getCost(), -actualUnit.getOffersCount());
            }
            repository.save(parent);
        }
    }

    /**
     * form list of tree. Tree represented as shopUnit with children recursively.
     * @param importRequest to turn into trees
     * @return list tree roots
     */
    private List<ShopUnit> formTrees(ShopUnitImportRequest importRequest) {
        Map<String, ShopUnit> importRequestMap = importRequest.convertToListUnits().stream().collect(Collectors.toMap(ShopUnit::getId, x -> x));
        List<ShopUnit> treeRoots = new ArrayList<>();
        for (ShopUnit value : importRequestMap.values()) {
            if (value.getParentId() == null || !importRequestMap.containsKey(value.getParentId())) {
                treeRoots.add(value);
            } else {
                importRequestMap.get(value.getParentId()).getChildren().add(value);
            }
        }
        return treeRoots;
    }

    /**
     * changes this cost and this countOffers in parents of this id recursively including id
     * @param id shopUnit from which changing is starting
     * @param cost this cost
     * @param count this countOffers
     */
    private void changeCostToParents(String id, long cost, int count) {
        Optional<ShopUnit> optUnit = repository.findById(id);
        if (optUnit.isEmpty()) {
            throw new IllegalArgumentException("There is no such id");
        }
        ShopUnit unit = optUnit.get();
        unit.updateCostAndCount(cost, count);
        repository.save(unit);
        if (unit.getParentId() != null) {
            changeCostToParents(unit.getParentId(), cost, count);
        }
    }

    /**
     * calculates cost and countOffers for all nodes in tree starting from last nodes(leafs).
     * @param unit tree root
     */
    private void calculateTreePrices(ShopUnit unit) {
        if (unit.getType() == ShopUnitType.OFFER) {
            return;
        }
        long sum = 0;
        int count = 0;
        for (ShopUnit child : unit.getChildren()) {
            calculateTreePrices(child);
            count += child.getOffersCount();
            sum += child.getPrice();
        }
        if (count != 0) {
            unit.updateCostAndCount(sum, count);
        }
    }

    /**
     * adds tree to repository. tree should be calculated by method calculateTreePrices.
     * @param unit tree root
     */
    private void addTreeToRepository(ShopUnit unit) {
        Optional<ShopUnit> actualUnitOpt = repository.findById(unit.getId());
        if (actualUnitOpt.isPresent()) {
            ShopUnit actualUnit = actualUnitOpt.get();
            unit.updateCostAndCount(actualUnit.getCost(), actualUnit.getOffersCount());
        }
        long costToAdd = unit.getCost();
        int countToAdd = unit.getOffersCount();
        repository.addUnitWithChildren(unit);
        ShopUnit child = unit;
        ShopUnit parent;
        while (true) {
            if (child.getParentId() == null) {
                return;
            }
            Optional<ShopUnit> parentOpt = repository.findById(child.getParentId());
            if (parentOpt.isEmpty()) {
                throw new IllegalArgumentException("Invalid unit: there is no such parent");
            }
            parent = parentOpt.get();
            parent.setDate(child.getDate());
            if (Objects.equals(parent.getId(), unit.getParentId())) {
                parent.addChild(unit);
            } else {
                parent.updateCostAndCount(costToAdd, countToAdd);
            }
            child = parent;
            repository.save(parent);
        }
    }

    /**
     * gets node with children recursively
     * @param id node id
     * @return optional of result of searching in database
     */
    public Optional<ShopUnit> getNode(String id) {
        return repository.findById(id);
    }

    /**
     * returns is this uuid valid.
     * @param uuid this uuid
     */
    public boolean isValidUUID(String uuid) {
        return uuidPattern.matcher(uuid).matches();
    }
}

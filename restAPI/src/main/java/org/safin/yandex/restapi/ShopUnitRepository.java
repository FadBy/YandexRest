package org.safin.yandex.restapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ShopUnitRepository extends JpaRepository<ShopUnit, String> {
    default void deleteUnitWithChildren(ShopUnit unit) {
        List<ShopUnit> toDelete = deleteUnitWithChildrenInjected(unit);
        if (unit.getParentId() != null) {
            Optional<ShopUnit> parentOpt = findById(unit.getParentId());
            if (parentOpt.isEmpty()) {
                throw new IllegalArgumentException("There is no such parent");
            }
            ShopUnit parent = parentOpt.get();
            parent.getChildren().remove(unit);
            save(parent);
        }
        deleteAll(toDelete);
    }

    private List<ShopUnit> deleteUnitWithChildrenInjected(ShopUnit unit) {
        if (unit.getType() == ShopUnitType.OFFER) {
            return List.of(unit);
        }
        List<ShopUnit> toDelete = new ArrayList<>();
        for (ShopUnit child : unit.getChildren()) {
            toDelete.addAll(deleteUnitWithChildrenInjected(child));
        }
        toDelete.addAll(unit.getChildren());
        unit.setChildren(new HashSet<>());
        save(unit);
        toDelete.add(unit);
        return toDelete;
    }

    default void addUnitWithChildren(ShopUnit unit) {
        if (unit.getChildren() != null) {
            for (ShopUnit child : unit.getChildren()) {
                addUnitWithChildren(child);
            }
        }
        save(unit);
    }
}

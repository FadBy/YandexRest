package org.safin.yandex.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ShopUnitImportRequestValidator implements Validator<ShopUnitImportRequest> {

    private final ShopUnitImportValidator validator;

    private final ShopUnitRepository repository;

    private final static DateTimeFormatter updateDateFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    //2022-05-28 21:12:01.000Z

    @Autowired
    public ShopUnitImportRequestValidator(ShopUnitImportValidator validator, ShopUnitRepository repository) {
        this.validator = validator;
        this.repository = repository;

    }

    @Override
    public boolean isValid(ShopUnitImportRequest unit) {
        if (unit.items().stream().anyMatch(x -> !validator.isValid(x))) {
            return false;
        }
        try {
            updateDateFormatter.parse(unit.updateDate());
        } catch (DateTimeParseException e) {
            return false;
        }
        Map<String, ShopUnitImport> itemsMap = new HashMap<>();
        for (ShopUnitImport unitImport : unit.items()) {
            if (itemsMap.containsKey(unitImport.getId())) {
                return false;
            }
            itemsMap.put(unitImport.getId(), unitImport);
        }
        if (itemsMap.size() != unit.items().size()) {
            return false;
        }
        for (ShopUnitImport value : itemsMap.values()) {
            if (repository.existsById(value.getId())) {
                ShopUnit actualUnit = repository.findById(value.getId()).get();
                if (actualUnit.getType() != value.getType()) {
                    return false;
                }
            }
            if (value.getParentId() == null) {
                continue;
            }
            ShopUnitType parentType;
            if (itemsMap.containsKey(value.getParentId())) {
                parentType = itemsMap.get(value.getParentId()).getType();
            } else {
                Optional<ShopUnit> optUnit = repository.findById(value.getParentId());
                if (optUnit.isEmpty()) {
                    return false;
                }
                parentType = optUnit.get().getType();
            }
            if (parentType == ShopUnitType.OFFER) {
                return false;
            }
        }
        return true;
    }
}

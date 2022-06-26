package org.safin.yandex.restapi;

import org.springframework.stereotype.Component;

import static org.safin.yandex.restapi.ShopUnitService.uuidPattern;

@Component
public class ShopUnitImportValidator implements Validator<ShopUnitImport> {

    @Override
    public boolean isValid(ShopUnitImport unit) {
        if (unit.getId() == null || unit.getName() == null ||
                !uuidPattern.matcher(unit.getId()).matches() || (unit.getParentId() != null && !uuidPattern.matcher(unit.getParentId()).matches()) ||
                unit.getType() == ShopUnitType.CATEGORY && unit.getPrice() != null ||
                unit.getType() == ShopUnitType.OFFER && (unit.getPrice() == null || unit.getPrice() < 0)) {
            return false;
        }
        return true;
    }
}

package org.safin.yandex.restapi;

import java.util.List;


public record ShopUnitImportRequest(List<ShopUnitImport> items,
                                    String updateDate) {

    public List<ShopUnit> convertToListUnits() {
        return items.stream().map(x -> x.convertToShopUnit(updateDate)).toList();
    }
}

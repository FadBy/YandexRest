package org.safin.yandex.restapi;

import java.util.List;


public record ShopUnitImportRequest(List<ShopUnitImport> items,
                                    String updateDate) {

    /**
     * converts this shopUnitImportRequest to list of shopUnits by list of shopUnitImports and this updateDate
     */
    public List<ShopUnit> convertToListUnits() {
        return items.stream().map(x -> x.convertToShopUnit(updateDate)).toList();
    }
}

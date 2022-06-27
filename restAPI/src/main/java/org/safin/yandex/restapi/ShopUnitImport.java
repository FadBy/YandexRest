package org.safin.yandex.restapi;

import java.util.HashSet;

public class ShopUnitImport {
    private final String id;
    private final String name;
    private final String parentId;
    private final ShopUnitType type;
    private final Long price;

    public ShopUnitImport(String id, String name, String parentId, ShopUnitType type, Long price) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.type = type;
        this.price = price;
    }

    /**
     * convert this shopUnitImport to shopUnit with this date and without children
     * @param date this date
     * @return converted shopUnitImport
     */
    public ShopUnit convertToShopUnit(String date) {
        HashSet<ShopUnit> children = null;
        if (type == ShopUnitType.CATEGORY) {
            children = new HashSet<>();
        }
        return new ShopUnit(id, name, date, parentId, type, price, children, 0);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParentId() {
        return parentId;
    }

    public ShopUnitType getType() {
        return type;
    }

    public Long getPrice() {
        return price;
    }
}

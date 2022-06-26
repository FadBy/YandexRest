package org.safin.yandex.restapi;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class ShopUnit {

    @Id
    @Column(
            name = "id",
            columnDefinition = "char(36)",
            nullable = false
    )
    private String id;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "date"
    )
    private String date;

    @Column(
            name = "parentId",
            columnDefinition = "char(36)"
    )
    private String parentId;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "type",
            nullable = false
    )
    private ShopUnitType type;

    @Column(
            name = "offersCount",
            nullable = false
    )
    private Integer offersCount = 0;

    @Column(
            name = "cost",
            nullable = false,
            columnDefinition = "bigint default 0"
    )
    private Long cost = 0L;

    @JsonIgnore
    public Long getCost() {
        if (cost == null) {
            return 0L;
        }
        return cost;
    }

    @PrePersist
    public void prePersist() {
        if (cost == null) {
            cost = 0L;
        }
        if (offersCount == null) {
            if (type == ShopUnitType.OFFER) {
                offersCount = 1;
            } else {
                offersCount = 0;
            }
        }
    }

    public void setCost(Long cost) {
        if (cost == null) {
            cost = 0L;
        }
        this.cost = cost;
    }

    public void setOffersCount(Integer offersCount) {
        if (type == ShopUnitType.OFFER) {
            return;
        }
        this.offersCount = offersCount;
    }

    public Set<ShopUnit> getChildren() {
        if (type == ShopUnitType.OFFER) {
            return null;
        }
        return children;
    }

    public void setChildren(Set<ShopUnit> children) {
        if (type == ShopUnitType.OFFER) {
            children = null;
        }
        this.children = children;
    }

    public void deleteChild(ShopUnit unit) {
        children.remove(unit);
        cost -= unit.getCost();
        offersCount -= unit.offersCount;
    }

    public void addChild(ShopUnit unit) {
        children.add(unit);
        if (cost == null) {
            cost = 0L;
        }
        cost += unit.getCost();
        offersCount += unit.offersCount;
    }

    @ManyToMany
    private Set<ShopUnit> children;

    public void updateCostAndCount(long costToAdd, int countToAdd) {
        if (cost == null) {
            cost = 0L;
        }
        cost += costToAdd;
        offersCount += countToAdd;
    }

    public ShopUnit(String id, String name, String date, String parentId, ShopUnitType type, Long cost, Set<ShopUnit> children, int offersCount) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.parentId = parentId;
        this.type = type;
        this.cost = cost;
        this.children = children;
        this.offersCount = offersCount;
        if (type == ShopUnitType.OFFER) {
            this.children = null;
            this.offersCount = 1;
        }
        if (cost == null) {
            cost = 0L;
        }
    }

    public ShopUnit(String id, String name, String date, String parentId, ShopUnitType type, Long cost, Set<ShopUnit> children) {
        this(id, name, date, parentId, type, cost, children, type == ShopUnitType.OFFER ? 1 : 0);
    }

    public ShopUnitType getType() {
        return type;
    }

    public void setType(ShopUnitType type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ShopUnit() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @JsonIgnore
    public int getOffersCount() {
        return offersCount;
    }

    public Long getPrice() {
        if (offersCount == 0) {
            return null;
        }
        return cost / offersCount;
    }
}

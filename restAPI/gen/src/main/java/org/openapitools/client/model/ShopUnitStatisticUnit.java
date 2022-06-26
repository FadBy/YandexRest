/*
 * Mega Market Open API
 * Вступительное задание в Летнюю Школу Бэкенд Разработки Яндекса 2022
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.UUID;
import org.openapitools.client.model.ShopUnitType;
import org.threeten.bp.OffsetDateTime;

/**
 * ShopUnitStatisticUnit
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-20T11:40:39.113915+03:00[Europe/Moscow]")
public class ShopUnitStatisticUnit {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private UUID id;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_PARENT_ID = "parentId";
  @SerializedName(SERIALIZED_NAME_PARENT_ID)
  private UUID parentId;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private ShopUnitType type;

  public static final String SERIALIZED_NAME_PRICE = "price";
  @SerializedName(SERIALIZED_NAME_PRICE)
  private Long price;

  public static final String SERIALIZED_NAME_DATE = "date";
  @SerializedName(SERIALIZED_NAME_DATE)
  private OffsetDateTime date;


  public ShopUnitStatisticUnit id(UUID id) {
    
    this.id = id;
    return this;
  }

   /**
   * Уникальный идентфикатор
   * @return id
  **/
  @ApiModelProperty(example = "3fa85f64-5717-4562-b3fc-2c963f66a333", required = true, value = "Уникальный идентфикатор")

  public UUID getId() {
    return id;
  }


  public void setId(UUID id) {
    this.id = id;
  }


  public ShopUnitStatisticUnit name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Имя элемента
   * @return name
  **/
  @ApiModelProperty(required = true, value = "Имя элемента")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public ShopUnitStatisticUnit parentId(UUID parentId) {
    
    this.parentId = parentId;
    return this;
  }

   /**
   * UUID родительской категории
   * @return parentId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "3fa85f64-5717-4562-b3fc-2c963f66a333", value = "UUID родительской категории")

  public UUID getParentId() {
    return parentId;
  }


  public void setParentId(UUID parentId) {
    this.parentId = parentId;
  }


  public ShopUnitStatisticUnit type(ShopUnitType type) {
    
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(required = true, value = "")

  public ShopUnitType getType() {
    return type;
  }


  public void setType(ShopUnitType type) {
    this.type = type;
  }


  public ShopUnitStatisticUnit price(Long price) {
    
    this.price = price;
    return this;
  }

   /**
   * Целое число, для категории - это средняя цена всех дочерних товаров(включая товары подкатегорий). Если цена является не целым числом, округляется в меньшую сторону до целого числа. Если категория не содержит товаров цена равна null.
   * @return price
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Целое число, для категории - это средняя цена всех дочерних товаров(включая товары подкатегорий). Если цена является не целым числом, округляется в меньшую сторону до целого числа. Если категория не содержит товаров цена равна null.")

  public Long getPrice() {
    return price;
  }


  public void setPrice(Long price) {
    this.price = price;
  }


  public ShopUnitStatisticUnit date(OffsetDateTime date) {
    
    this.date = date;
    return this;
  }

   /**
   * Время последнего обновления элемента.
   * @return date
  **/
  @ApiModelProperty(required = true, value = "Время последнего обновления элемента.")

  public OffsetDateTime getDate() {
    return date;
  }


  public void setDate(OffsetDateTime date) {
    this.date = date;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShopUnitStatisticUnit shopUnitStatisticUnit = (ShopUnitStatisticUnit) o;
    return Objects.equals(this.id, shopUnitStatisticUnit.id) &&
        Objects.equals(this.name, shopUnitStatisticUnit.name) &&
        Objects.equals(this.parentId, shopUnitStatisticUnit.parentId) &&
        Objects.equals(this.type, shopUnitStatisticUnit.type) &&
        Objects.equals(this.price, shopUnitStatisticUnit.price) &&
        Objects.equals(this.date, shopUnitStatisticUnit.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, parentId, type, price, date);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShopUnitStatisticUnit {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}


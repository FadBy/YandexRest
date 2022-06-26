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
import java.util.ArrayList;
import java.util.List;
import org.openapitools.client.model.ShopUnitImport;
import org.threeten.bp.OffsetDateTime;

/**
 * ShopUnitImportRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-20T11:40:39.113915+03:00[Europe/Moscow]")
public class ShopUnitImportRequest {
  public static final String SERIALIZED_NAME_ITEMS = "items";
  @SerializedName(SERIALIZED_NAME_ITEMS)
  private List<ShopUnitImport> items = null;

  public static final String SERIALIZED_NAME_UPDATE_DATE = "updateDate";
  @SerializedName(SERIALIZED_NAME_UPDATE_DATE)
  private OffsetDateTime updateDate;


  public ShopUnitImportRequest items(List<ShopUnitImport> items) {
    
    this.items = items;
    return this;
  }

  public ShopUnitImportRequest addItemsItem(ShopUnitImport itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<ShopUnitImport>();
    }
    this.items.add(itemsItem);
    return this;
  }

   /**
   * Импортируемые элементы
   * @return items
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Импортируемые элементы")

  public List<ShopUnitImport> getItems() {
    return items;
  }


  public void setItems(List<ShopUnitImport> items) {
    this.items = items;
  }


  public ShopUnitImportRequest updateDate(OffsetDateTime updateDate) {
    
    this.updateDate = updateDate;
    return this;
  }

   /**
   * Время обновления добавляемых товаров/категорий.
   * @return updateDate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "2022-05-28T21:12:01Z", value = "Время обновления добавляемых товаров/категорий.")

  public OffsetDateTime getUpdateDate() {
    return updateDate;
  }


  public void setUpdateDate(OffsetDateTime updateDate) {
    this.updateDate = updateDate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShopUnitImportRequest shopUnitImportRequest = (ShopUnitImportRequest) o;
    return Objects.equals(this.items, shopUnitImportRequest.items) &&
        Objects.equals(this.updateDate, shopUnitImportRequest.updateDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, updateDate);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShopUnitImportRequest {\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("    updateDate: ").append(toIndentedString(updateDate)).append("\n");
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

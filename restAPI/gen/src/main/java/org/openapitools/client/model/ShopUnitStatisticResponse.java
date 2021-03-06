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
import org.openapitools.client.model.ShopUnitStatisticUnit;

/**
 * ShopUnitStatisticResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-20T11:40:39.113915+03:00[Europe/Moscow]")
public class ShopUnitStatisticResponse {
  public static final String SERIALIZED_NAME_ITEMS = "items";
  @SerializedName(SERIALIZED_NAME_ITEMS)
  private List<ShopUnitStatisticUnit> items = null;


  public ShopUnitStatisticResponse items(List<ShopUnitStatisticUnit> items) {
    
    this.items = items;
    return this;
  }

  public ShopUnitStatisticResponse addItemsItem(ShopUnitStatisticUnit itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<ShopUnitStatisticUnit>();
    }
    this.items.add(itemsItem);
    return this;
  }

   /**
   * История в произвольном порядке.
   * @return items
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "История в произвольном порядке.")

  public List<ShopUnitStatisticUnit> getItems() {
    return items;
  }


  public void setItems(List<ShopUnitStatisticUnit> items) {
    this.items = items;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShopUnitStatisticResponse shopUnitStatisticResponse = (ShopUnitStatisticResponse) o;
    return Objects.equals(this.items, shopUnitStatisticResponse.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShopUnitStatisticResponse {\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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


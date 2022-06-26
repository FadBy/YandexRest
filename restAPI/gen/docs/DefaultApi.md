# DefaultApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteIdDelete**](DefaultApi.md#deleteIdDelete) | **DELETE** /delete/{id} | 
[**importsPost**](DefaultApi.md#importsPost) | **POST** /imports | 
[**nodeIdStatisticGet**](DefaultApi.md#nodeIdStatisticGet) | **GET** /node/{id}/statistic | 
[**nodesIdGet**](DefaultApi.md#nodesIdGet) | **GET** /nodes/{id} | 
[**salesGet**](DefaultApi.md#salesGet) | **GET** /sales | 


<a name="deleteIdDelete"></a>
# **deleteIdDelete**
> deleteIdDelete(id)



Удалить элемент по идентификатору. При удалении категории удаляются все дочерние элементы. Доступ к статистике (истории обновлений) удаленного элемента невозможен.  Так как время удаления не передается, при удалении элемента время обновления родителя изменять не нужно.  **Обратите, пожалуйста, внимание на этот обработчик. При его некорректной работе тестирование может быть невозможно.** 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    UUID id = new UUID(); // UUID | Идентификатор
    try {
      apiInstance.deleteIdDelete(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#deleteIdDelete");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**UUID**](.md)| Идентификатор |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Удаление прошло успешно. |  -  |
**400** | Невалидная схема документа или входные данные не верны. |  -  |
**404** | Категория/товар не найден. |  -  |

<a name="importsPost"></a>
# **importsPost**
> importsPost(shopUnitImportRequest)



Импортирует новые товары и/или категории. Товары/категории импортированные повторно обновляют текущие. Изменение типа элемента с товара на категорию или с категории на товар не допускается. Порядок элементов в запросе является произвольным.    - uuid товара или категории является уникальным среди товаров и категорий   - родителем товара или категории может быть только категория   - принадлежность к категории определяется полем parentId   - товар или категория могут не иметь родителя (при обновлении parentId на null, элемент остается без родителя)   - название элемента не может быть null   - у категорий поле price должно содержать null   - цена товара не может быть null и должна быть больше либо равна нулю.   - при обновлении товара/категории обновленными считаются **все** их параметры   - при обновлении параметров элемента обязательно обновляется поле **date** в соответствии с временем обновления   - в одном запросе не может быть двух элементов с одинаковым id   - дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400.  Гарантируется, что во входных данных нет циклических зависимостей и поле updateDate монотонно возрастает. Гарантируется, что при проверке передаваемое время кратно секундам. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    ShopUnitImportRequest shopUnitImportRequest = new ShopUnitImportRequest(); // ShopUnitImportRequest | 
    try {
      apiInstance.importsPost(shopUnitImportRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#importsPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **shopUnitImportRequest** | [**ShopUnitImportRequest**](ShopUnitImportRequest.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Вставка или обновление прошли успешно. |  -  |
**400** | Невалидная схема документа или входные данные не верны. |  -  |

<a name="nodeIdStatisticGet"></a>
# **nodeIdStatisticGet**
> ShopUnitStatisticResponse nodeIdStatisticGet(id, dateStart, dateEnd)



Получение статистики (истории обновлений) по товару/категории за заданный полуинтервал [from, to). Статистика по удаленным элементам недоступна.  - цена категории - это средняя цена всех её товаров, включая товары дочерних категорий.Если категория не содержит товаров цена равна null. При обновлении цены товара, средняя цена категории, которая содержит этот товар, тоже обновляется. - можно получить статистику за всё время. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    UUID id = new UUID(); // UUID | UUID товара/категории для которой будет отображаться статистика
    OffsetDateTime dateStart = OffsetDateTime.now(); // OffsetDateTime | Дата и время начала интервала, для которого считается статистика. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400.
    OffsetDateTime dateEnd = OffsetDateTime.now(); // OffsetDateTime | Дата и время конца интервала, для которого считается статистика. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400.
    try {
      ShopUnitStatisticResponse result = apiInstance.nodeIdStatisticGet(id, dateStart, dateEnd);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#nodeIdStatisticGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**UUID**](.md)| UUID товара/категории для которой будет отображаться статистика |
 **dateStart** | **OffsetDateTime**| Дата и время начала интервала, для которого считается статистика. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400. | [optional]
 **dateEnd** | **OffsetDateTime**| Дата и время конца интервала, для которого считается статистика. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400. | [optional]

### Return type

[**ShopUnitStatisticResponse**](ShopUnitStatisticResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Статистика по элементу. |  -  |
**400** | Некорректный формат запроса или некорректные даты интервала. |  -  |
**404** | Категория/товар не найден. |  -  |

<a name="nodesIdGet"></a>
# **nodesIdGet**
> ShopUnit nodesIdGet(id)



Получить информацию об элементе по идентификатору. При получении информации о категории также предоставляется информация о её дочерних элементах.  - для пустой категории поле children равно пустому массиву, а для товара равно null - цена категории - это средняя цена всех её товаров, включая товары дочерних категорий. Если категория не содержит товаров цена равна null. При обновлении цены товара, средняя цена категории, которая содержит этот товар, тоже обновляется. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    UUID id = new UUID(); // UUID | Идентификатор элемента
    try {
      ShopUnit result = apiInstance.nodesIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#nodesIdGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**UUID**](.md)| Идентификатор элемента |

### Return type

[**ShopUnit**](ShopUnit.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Информация об элементе. |  -  |
**400** | Невалидная схема документа или входные данные не верны. |  -  |
**404** | Категория/товар не найден. |  -  |

<a name="salesGet"></a>
# **salesGet**
> ShopUnitStatisticResponse salesGet(date)



Получение списка **товаров**, цена которых была обновлена за последние 24 часа включительно [now() - 24h, now()] от времени переданном в запросе. Обновление цены не означает её изменение. Обновления цен удаленных товаров недоступны. При обновлении цены товара, средняя цена категории, которая содержит этот товар, тоже обновляется. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    OffsetDateTime date = OffsetDateTime.now(); // OffsetDateTime | Дата и время запроса. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400
    try {
      ShopUnitStatisticResponse result = apiInstance.salesGet(date);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#salesGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **date** | **OffsetDateTime**| Дата и время запроса. Дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400 |

### Return type

[**ShopUnitStatisticResponse**](ShopUnitStatisticResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Список товаров, цена которых была обновлена. |  -  |
**400** | Невалидная схема документа или входные данные не верны. |  -  |


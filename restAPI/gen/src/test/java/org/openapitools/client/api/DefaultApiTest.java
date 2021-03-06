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


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.Error;
import org.threeten.bp.OffsetDateTime;
import org.openapitools.client.model.ShopUnit;
import org.openapitools.client.model.ShopUnitImportRequest;
import org.openapitools.client.model.ShopUnitStatisticResponse;
import java.util.UUID;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for DefaultApi
 */
@Ignore
public class DefaultApiTest {

    private final DefaultApi api = new DefaultApi();

    
    /**
     * 
     *
     * Удалить элемент по идентификатору. При удалении категории удаляются все дочерние элементы. Доступ к статистике (истории обновлений) удаленного элемента невозможен.  Так как время удаления не передается, при удалении элемента время обновления родителя изменять не нужно.  **Обратите, пожалуйста, внимание на этот обработчик. При его некорректной работе тестирование может быть невозможно.** 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteIdDeleteTest() throws ApiException {
        UUID id = null;
        api.deleteIdDelete(id);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Импортирует новые товары и/или категории. Товары/категории импортированные повторно обновляют текущие. Изменение типа элемента с товара на категорию или с категории на товар не допускается. Порядок элементов в запросе является произвольным.    - uuid товара или категории является уникальным среди товаров и категорий   - родителем товара или категории может быть только категория   - принадлежность к категории определяется полем parentId   - товар или категория могут не иметь родителя (при обновлении parentId на null, элемент остается без родителя)   - название элемента не может быть null   - у категорий поле price должно содержать null   - цена товара не может быть null и должна быть больше либо равна нулю.   - при обновлении товара/категории обновленными считаются **все** их параметры   - при обновлении параметров элемента обязательно обновляется поле **date** в соответствии с временем обновления   - в одном запросе не может быть двух элементов с одинаковым id   - дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400.  Гарантируется, что во входных данных нет циклических зависимостей и поле updateDate монотонно возрастает. Гарантируется, что при проверке передаваемое время кратно секундам. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void importsPostTest() throws ApiException {
        ShopUnitImportRequest shopUnitImportRequest = null;
        api.importsPost(shopUnitImportRequest);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Получение статистики (истории обновлений) по товару/категории за заданный полуинтервал [from, to). Статистика по удаленным элементам недоступна.  - цена категории - это средняя цена всех её товаров, включая товары дочерних категорий.Если категория не содержит товаров цена равна null. При обновлении цены товара, средняя цена категории, которая содержит этот товар, тоже обновляется. - можно получить статистику за всё время. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void nodeIdStatisticGetTest() throws ApiException {
        UUID id = null;
        OffsetDateTime dateStart = null;
        OffsetDateTime dateEnd = null;
        ShopUnitStatisticResponse response = api.nodeIdStatisticGet(id, dateStart, dateEnd);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Получить информацию об элементе по идентификатору. При получении информации о категории также предоставляется информация о её дочерних элементах.  - для пустой категории поле children равно пустому массиву, а для товара равно null - цена категории - это средняя цена всех её товаров, включая товары дочерних категорий. Если категория не содержит товаров цена равна null. При обновлении цены товара, средняя цена категории, которая содержит этот товар, тоже обновляется. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void nodesIdGetTest() throws ApiException {
        UUID id = null;
        ShopUnit response = api.nodesIdGet(id);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Получение списка **товаров**, цена которых была обновлена за последние 24 часа включительно [now() - 24h, now()] от времени переданном в запросе. Обновление цены не означает её изменение. Обновления цен удаленных товаров недоступны. При обновлении цены товара, средняя цена категории, которая содержит этот товар, тоже обновляется. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void salesGetTest() throws ApiException {
        OffsetDateTime date = null;
        ShopUnitStatisticResponse response = api.salesGet(date);

        // TODO: test validations
    }
    
}

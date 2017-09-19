package cn.eva.mini.application.service.product;

import cn.eva.mini.application.dto.data.DeviceDataView;
import cn.eva.mini.application.dto.product.ProductDraft;
import cn.eva.mini.application.dto.product.ProductView;
import cn.eva.mini.application.dto.product.mapper.ProductMapper;
import cn.eva.mini.domain.entity.Product;
import cn.eva.mini.domain.service.ProductService;
import cn.eva.mini.domain.service.ProductTypeService;
import cn.eva.mini.infra.util.RedisUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用于增删改Product.
 */
@Service
public class ProductApplication {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(ProductApplication.class);

  /**
   * ProductService.
   */
  @Autowired
  private transient ProductService productService;

  /**
   * The redis template.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * ProductType service.
   */
  @Autowired
  private transient ProductTypeService productTypeService;

  /**
   * Create Product.
   *
   * @param draft       product draft
   * @param developerId the developer id
   * @return product view
   */
  @Transactional
  public ProductView create(ProductDraft draft, String developerId) {
    LOGGER.debug("Enter. developerId: {}, draft: {}.", developerId, draft);

    // 1. 检查名字是否重复
    productService.isExistName(developerId, draft.getName());

    // 2. 检查类型是否存在
    productTypeService.exists(draft.getProductTypeId());

    // 3. 生成实体对象
    Product product = ProductMapper.toModel(draft, developerId);

    product = productService.save(product);

    clearCacheForDeveloper(developerId);

    ProductView view = ProductMapper.toView(product);

    LOGGER.debug("Exit. productView: {}.", view);
    return view;
  }

  /**
   * 删除Product。
   *
   * @param id          product id
   * @param developerId the developer id
   * @param version     the version
   */
  public void delete(String id, String developerId, Integer version) {
    LOGGER.debug("Enter. id: {}, developerId: {}, version: {}.", id, developerId, version);

    Product product = productService.get(id);

//    checkForUpdateAndDelete(developerId, product, version);

    productService.delete(id);

    clearCacheForDeveloper(developerId);

//    restClient.deleteAllDataDefinition(developerId, product.getId());

    LOGGER.debug("Exit.");
  }

  /**
   * Update product with actions.
   *
   * @param id the id
   * @param developerId the developer id
   * @param version the version
   * @param actions the actions
   * @return the product view
   */
//  public ProductView update(String id, String developerId, Integer version, List<UpdateAction>
//      actions) {
//    LOGGER.debug("Enter: id: {}, version: {}, actions: {}.", id, version, actions);
//
//    Product valueInDb = productService.get(id);
//
//    checkForUpdateAndDelete(developerId, valueInDb, version);
//
//    actions.stream().forEach(action -> updaterService.handle(valueInDb, action));
//
//    Product product = productService.save(valueInDb);
//
//    cacheApplication.clearCacheForDeveloper(developerId);
//
//    List<ProductDataView> productDataViews = restClient.getProductData(developerId, id);
//
//    ProductView updatedProduct = ProductMapper.toView(product);
//
//    updatedProduct.setDataDefinitions(productDataViews);
//
//    updatedProduct.getDataDefinitions().stream().forEach(
//        data -> data.setDataSchema(JsonUtils.deserialize(data.getSchema(), JsonNode.class))
//    );
//
//    LOGGER.debug("Exit: updated product: {}", updatedProduct);
//    return updatedProduct;
//  }

  /**
   * 在update和delete中，需要检查developer是否一致，version是否一致，status是否合法。
   */
//  private void checkForUpdateAndDelete(String developerId, Product product, Integer version) {
//    ProductValidator.checkDeveloper(developerId, product);
//
//    ProductValidator.checkStatus(product);
//
//    VersionValidator.validate(version, product.getVersion());
//  }


  /**
   * Get all product by developer id.
   *
   * @param developerId developer id
   * @return list build product view
   */
  public List<ProductView> getAllByDeveloperId(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    List<ProductView> result = getProductsFromCache(developerId);

    if (result.isEmpty()) {
      LOGGER.debug("Cache fail, get from database.");
      result = fetchProductsFromDB(developerId);
    }

//    result.stream().forEach(
//        productView -> productView.getDataDefinitions().stream().forEach(
//            data -> data.setDataSchema(JsonUtils.deserialize(data.getSchema(), JsonNode.class))
//        )
//    );

    LOGGER.trace("products: {}.", result);
    LOGGER.debug("Exit. product Size: {}.", result.size());
    return result;
  }

  /**
   * Get product by id.
   *
   * @param id          String product id
   * @param developerId the developer id
   * @return the product view
   */
  public ProductView get(String id, String developerId) {
    LOGGER.debug("Enter. id: {}, developerId: {}.", id, developerId);


    String key = String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId);

    ProductView result = (ProductView) redisTemplate.opsForHash().get(key, id);

    if (result == null) {
      LOGGER.debug("Get from cache failed, get from database.");

      List<ProductView> productViews = fetchProductsFromDB(developerId);

      result = productViews.stream().filter(view -> id.equals(view.getId())).findAny().orElse(null);
    }

    LOGGER.debug("Exit. productView: {}.", result);
    return result;
  }

  /**
   * Cache products.
   *
   * @param developerId the developer id
   * @param products    the products
   */
  @Async
  private void cacheProducts(String developerId, List<ProductView> products) {
    LOGGER.debug("Enter. products size: {}.", products.size());

    Map<String, ProductView> cacheProducts = Maps.newHashMap();
    products.stream().forEach(view -> cacheProducts.put(view.getId(), view));

    redisTemplate.opsForHash().putAll(String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId), cacheProducts);

    LOGGER.debug("Exit. cache done.");
  }

  /**
   * Delete products by developerId.
   *
   * @param developerId the developer id
   */
  @Async
  private void clearCacheForDeveloper(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    redisTemplate.delete(String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId));

    LOGGER.debug("Exit. delete done.");
  }


  /**
   * Gets products by developerId.
   *
   * @param developerId the developer id
   * @return the products
   */
  private List<ProductView> getProductsFromCache(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    List<ProductView> result = Lists.newArrayList();

    String key = String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId);
    Map<String, Object> cacheProducts = redisTemplate.opsForHash().entries(key);

    if (!CollectionUtils.isEmpty(cacheProducts)) {
      cacheProducts.entrySet().stream().forEach(
        entry -> result.add((ProductView) entry.getValue())
      );
    }

    LOGGER.trace("Products: {}.", result);
    LOGGER.debug("Exit. products size: {}.", result.size());
    return result;
  }

  /**
   * Gets data definitions by productId.
   *
   * @param developerId the developer id
   * @param productId   the product id
   * @return the data definitions
   */
  public List<DeviceDataView> getDataDefinitions(String developerId, String productId) {
    LOGGER.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    List<DeviceDataView> dataViews = Lists.newArrayList();
    ProductView product = get(productId, developerId);

    if (product != null && product.getDataDefinitions() != null) {
      dataViews = product.getDataDefinitions();
    }

    LOGGER.debug("Exit. dataDefinition size: {}.", dataViews.size());

    return dataViews;
  }

  /**
   * Fetch product by developerId.
   *
   * @param developerId the developerId
   * @return list build ProductView
   */
  private List<ProductView> fetchProductsFromDB(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    List<Product> products = productService.getByDeveloperId(developerId);

    List<ProductView> result = ProductMapper.toView(products);

    List<String> productIds = products.stream().map(Product::getId).collect(Collectors.toList());

    Map<String, List<DeviceDataView>> productDataViews = null;
//    Map<String, List<ProductDataView>> productDataViews = restClient.getProductData(developerId, productIds);

    mergeProductData(result, productDataViews);

    cacheProducts(developerId, result);

    LOGGER.trace("Product: {}.", result);
    LOGGER.debug("Exit. product size: {}.", result.size());

    return result;
  }

  /**
   * Merge ProductData into Product.
   *
   * @param productViews     list build Product
   * @param productDataViews list build ProductData
   */
  private void mergeProductData(List<ProductView> productViews,
                                Map<String, List<DeviceDataView>> productDataViews) {
    LOGGER.debug("Enter.");

    productViews.stream().forEach(
      product -> product.setDataDefinitions(productDataViews.get(product.getId())));

    LOGGER.debug("Exit.");
  }
}

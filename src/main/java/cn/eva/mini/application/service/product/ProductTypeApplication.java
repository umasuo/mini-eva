package cn.eva.mini.application.service.product;

import cn.eva.mini.application.dto.product.ProductTypeDraft;
import cn.eva.mini.application.dto.product.ProductTypeView;
import cn.eva.mini.application.dto.product.mapper.ProductTypeMapper;
import cn.eva.mini.domain.entity.ProductType;
import cn.eva.mini.domain.service.ProductTypeService;
import cn.eva.mini.infra.exception.NotExistException;
import cn.eva.mini.infra.util.RedisUtils;
import cn.eva.mini.infra.util.VersionValidator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ProductType application.
 */
@Service
public class ProductTypeApplication {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeApplication.class);

  /**
   * ProductType service.
   */
  @Autowired
  private transient ProductTypeService productTypeService;

  /**
   * The redis template.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * Create product type view.
   *
   * @param productTypeDraft the product type draft
   * @return the product type view
   */
  public ProductTypeView create(ProductTypeDraft productTypeDraft) {
    LOGGER.debug("Enter. productTypeDraft: {}.", productTypeDraft);

    ProductType productType = ProductTypeMapper.toModel(productTypeDraft);

    ProductType newProductType = productTypeService.save(productType);

    ProductTypeView result = ProductTypeMapper.toView(newProductType);

    // Reset the product cache
    deleteProductTypes();

    LOGGER.debug("Exit. new productType id: {}.", result.getId());
    return result;
  }

  /**
   * Delete ProductType by it's id.
   *
   * @param id      the id
   * @param version the version
   */
  public void delete(String id, Integer version) {
    LOGGER.debug("Enter. product type id: {}, version: {}.", id, version);

    ProductType productType = productTypeService.getById(id);

    VersionValidator.validate(version, productType.getVersion());

    productTypeService.delete(id);

    // TODO: 17/9/19 delete product type's data

    deleteProductTypes();

    LOGGER.debug("Exit.");
  }

  /**
   * Update product type view.
   *
   * @param id the id
   * @param version the version
   * @param actions the actions
   * @return the product type view
   */
//  public ProductTypeView update(String id, Integer version, List<UpdateAction> actions) {
//    LOGGER.debug("Enter: id: {}, version: {}, actions: {}.", id, version, actions);
//
//    ProductType valueInDb = productTypeService.getById(id);
//
//    VersionValidator.validate(version, valueInDb.getVersion());
//
//    actions.stream().forEach(action -> updaterService.handle(valueInDb, action));
//
//    ProductType product = productTypeService.save(valueInDb);
//
//    cacheApplication.deleteProductTypes();
//
//    Map<String, List<CommonDataView>> dataDefinitionViews =
//        restClient.getProductTypeData();
//
//    ProductTypeView updatedProduct = ProductTypeMapper.toView(product, dataDefinitionViews);
//
//    handleSchema(updatedProduct);
//
//    LOGGER.trace("updated productType: {}", updatedProduct);
//    LOGGER.debug("Exit.");
//
//    return updatedProduct;
//  }

  /**
   * 查询所有的产品类型。
   *
   * @return list build ProductType
   */
  public List<ProductTypeView> getAll() {
    LOGGER.debug("Enter.");

    // get from redis
//    List<ProductTypeView> cacheProductTypes = cacheApplication.getAllProductType();
//
//    if (cacheProductTypes.isEmpty()) {
//      LOGGER.debug("Cache fail. Get from database.");
//      List<ProductType> productTypes = productTypeService.getAll();
//
//      // 调用data-definition的api获取对应id的CommonDataView
//      Map<String, List<CommonDataView>> dataDefinitionViews =
//          restClient.getProductTypeData();
//
//      cacheProductTypes = ProductTypeMapper.toView(productTypes, dataDefinitionViews);
//
//      cacheApplication.cacheProductType(cacheProductTypes);
//
//    }
//
//    handleSchema(cacheProductTypes);
//
//    LOGGER.debug("Exit. productType size: {}.", cacheProductTypes.size());
//    return cacheProductTypes;
    return null;
  }

  /**
   * Get ProductType by it's id.
   *
   * @param id the id
   * @return the product type view
   */
  public ProductTypeView get(String id) {
    LOGGER.debug("Enter. id: {}.", id);
    List<ProductTypeView> productTypeViews = getAll();

    ProductTypeView result =
      productTypeViews.stream().filter(productTypeView -> id.equals(productTypeView.getId()))
        .findAny().orElse(null);

    if (result == null) {
      LOGGER.debug("Can not find productType: {}.", id);
      throw new NotExistException("ProductType not exist");
    }

    LOGGER.debug("Exit. productType: {}.", result);
    return result;
  }


  /**
   * Gets all ProductType.
   *
   * @return the all product type
   */
  public List<ProductTypeView> getAllProductType() {
    LOGGER.debug("Enter.");
    List<ProductTypeView> result = Lists.newArrayList();

    Map<String, ProductTypeView> cacheProductTypes =
      redisTemplate.opsForHash().entries(RedisUtils.PRODUCT_TYPE_KEY);

    if (!CollectionUtils.isEmpty(cacheProductTypes)) {
      result = cacheProductTypes.values().stream().collect(Collectors.toList());
    }

    LOGGER.trace("ProductType: {}.", result);
    LOGGER.debug("Exit. productType size: {}.", result.size());
    return result;
  }

  /**
   * Cache ProductType.
   *
   * @param productTypeViews the product type views
   */
  @Async
  public void cacheProductType(List<ProductTypeView> productTypeViews) {
    LOGGER.debug("Enter. productType size: {}.", productTypeViews.size());

    Map<String, ProductTypeView> cacheProductTypes = Maps.newHashMap();
    productTypeViews.stream().forEach(view -> cacheProductTypes.put(view.getId(), view));
    redisTemplate.opsForHash().putAll(RedisUtils.PRODUCT_TYPE_KEY, cacheProductTypes);

    LOGGER.debug("Exit. cache done.");
  }

  /**
   * Delete ProductType from cache.
   */
  @Async
  public void deleteProductTypes() {
    LOGGER.debug("Enter.");

    redisTemplate.delete(RedisUtils.PRODUCT_TYPE_KEY);

    LOGGER.debug("Exit. delete done.");
  }


}

package cn.eva.mini.application.service;

import cn.eva.mini.application.dto.product.ProductTypeView;
import cn.eva.mini.application.dto.product.ProductView;
import cn.eva.mini.infra.util.RedisUtils;
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
 * 用于处理缓存。
 */
@Service
public class ProductCacheApplication {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DataCacheApplication.class);

  /**
   * The redis template.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

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

  /**
   * Gets products by developerId.
   *
   * @param developerId the developer id
   * @return the products
   */
  public List<ProductView> getProducts(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    List<ProductView> result = Lists.newArrayList();

    String key = String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId);
    Map<String, Object> cacheProducts = redisTemplate.opsForHash().entries(key);

    if (! CollectionUtils.isEmpty(cacheProducts)) {
      cacheProducts.entrySet().stream().forEach(
          entry -> result.add((ProductView) entry.getValue())
      );
    }

    LOGGER.trace("Products: {}.", result);
    LOGGER.debug("Exit. products size: {}.", result.size());
    return result;
  }

  /**
   * Cache products.
   *
   * @param developerId the developer id
   * @param products the products
   */
  @Async
  public void cacheProducts(String developerId, List<ProductView> products) {
    LOGGER.debug("Enter. products size: {}.", products.size());

    Map<String, ProductView> cacheProducts = Maps.newHashMap();
    products.stream().forEach(view -> cacheProducts.put(view.getId(), view));

    redisTemplate.opsForHash()
        .putAll(String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId), cacheProducts);

    LOGGER.debug("Exit. cache done.");
  }

  /**
   * Gets product by it's id and developerId.
   *
   * @param developerId the developer id
   * @param productId the product id
   * @return the product by id
   */
  public ProductView getProductById(String developerId, String productId) {
    LOGGER.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    String key = String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId);

    ProductView result = (ProductView) redisTemplate.opsForHash().get(key, productId);

    LOGGER.debug("Exit. product: {}.", result);
    return result;
  }

  /**
   * Delete products by developerId.
   *
   * @param developerId the developer id
   */
  @Async
  public void deleteProducts(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    redisTemplate.delete(String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId));

    LOGGER.debug("Exit. delete done.");
  }
}

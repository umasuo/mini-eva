package cn.eva.mini.application.service;

import cn.eva.mini.application.dto.product.ProductDataView;
import cn.eva.mini.application.dto.product.ProductView;
import cn.eva.mini.application.dto.mapper.ProductMapper;
import cn.eva.mini.domain.entity.Product;
import cn.eva.mini.domain.service.ProductService;
import cn.eva.mini.infra.util.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用于查询Product.
 */
@Service
public class ProductQueryApplication {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(ProductQueryApplication.class);

  /**
   * ProductService.
   */
  @Autowired
  private transient ProductService productService;

  /**
   * DataCacheApplication.
   */
  @Autowired
  private transient ProductCacheApplication cacheApplication;

  /**
   * Get product by id.
   *
   * @param id String
   * @param developerId the developer id
   * @return the product view
   */
  public ProductView get(String id, String developerId) {
    LOGGER.debug("Enter. id: {}, developerId: {}.", id, developerId);

    ProductView result = cacheApplication.getProductById(developerId, id);

    if (result == null) {
      LOGGER.debug("Cache fail, get from database.");

      List<ProductView> productViews = fetchProducts(developerId);

      result = productViews.stream().filter(view -> id.equals(view.getId())).findAny().orElse(null);
    }

    LOGGER.debug("Exit. productView: {}.", result);
    return result;
  }

  /**
   * Get all product by developer id.
   *
   * @param developerId developer id
   * @return list build product view
   */
  public List<ProductView> getAllByDeveloperId(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    List<ProductView> result = cacheApplication.getProducts(developerId);

    if (result.isEmpty()) {
      LOGGER.debug("Cache fail, get from database.");
      result = fetchProducts(developerId);
    }

    result.stream().forEach(
        productView -> productView.getDataDefinitions().stream().forEach(
            data -> data.setDataSchema(JsonUtils.deserialize(data.getSchema(), JsonNode.class))
        )
    );

    LOGGER.trace("products: {}.", result);
    LOGGER.debug("Exit. product Size: {}.", result.size());
    return result;
  }

  /**
   * Gets data definitions by productId.
   *
   * @param developerId the developer id
   * @param productId the product id
   * @return the data definitions
   */
  public List<ProductDataView> getDataDefinitions(String developerId, String productId) {
    LOGGER.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    List<ProductDataView> dataViews = Lists.newArrayList();
    ProductView product = get(productId, developerId);

    if (product != null && product.getDataDefinitions() != null) {
      dataViews = product.getDataDefinitions();
    }

    LOGGER.debug("Exit. dataDefinition size: {}.", dataViews.size());

    return dataViews;
  }

  /**
   * Get all open product define by developer id.
   * 接口比较少用，暂时不需要使用缓存。
   *
   * @param id developer id
   * @return list build product view
   */
  public List<ProductView> getAllOpenProduct(String id) {
    LOGGER.debug("Enter. developerId: {}.", id);

    List<Product> products = productService.getAllOpenProduct(id);
    List<ProductView> views = ProductMapper.toView(products);

    LOGGER.trace("products: {}.", views);
    LOGGER.debug("Exit. product Size: {}.", views.size());
    return views;
  }

  /**
   * Count products.
   *
   * @return Long
   */
  public Long countProducts() {
    LOGGER.debug("Enter.");

    Long count = productService.countProducts();

    LOGGER.debug("Exit. product countProducts: {}.", count);

    return count;
  }

  /**
   * Fetch product by developerId.
   *
   * @param developerId the developerId
   * @return list build ProductView
   */
  private List<ProductView> fetchProducts(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    List<Product> products = productService.getByDeveloperId(developerId);

    List<ProductView> result = ProductMapper.toView(products);

    List<String> productIds = products.stream().map(Product::getId).collect(Collectors.toList());

    Map<String, List<ProductDataView>> productDataViews = null;
//    Map<String, List<ProductDataView>> productDataViews = restClient.getProductData(developerId, productIds);

    mergeProductData(result, productDataViews);

    cacheApplication.cacheProducts(developerId, result);

    LOGGER.trace("Product: {}.", result);
    LOGGER.debug("Exit. product size: {}.", result.size());

    return result;
  }

  /**
   * Merge ProductData into Product.
   *
   * @param productViews list build Product
   * @param productDataViews list build ProductData
   */
  private void mergeProductData(List<ProductView> productViews,
      Map<String, List<ProductDataView>> productDataViews) {
    LOGGER.debug("Enter.");

    productViews.stream().forEach(
        product -> product.setDataDefinitions(productDataViews.get(product.getId())));

    LOGGER.debug("Exit.");
  }
}

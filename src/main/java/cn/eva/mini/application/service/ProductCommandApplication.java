package cn.eva.mini.application.service;

import cn.eva.mini.application.dto.product.ProductDraft;
import cn.eva.mini.application.dto.product.ProductView;
import cn.eva.mini.application.dto.mapper.ProductMapper;
import cn.eva.mini.domain.entity.Product;
import cn.eva.mini.domain.service.ProductService;
import cn.eva.mini.domain.service.ProductTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于增删改Product.
 */
@Service
public class ProductCommandApplication {

  /**
   * Logger.
   */
  private final static Logger LOG = LoggerFactory.getLogger(ProductCommandApplication.class);

  /**
   * ProductService.
   */
  @Autowired
  private transient ProductService productService;


  /**
   * ProductType service.
   */
  @Autowired
  private transient ProductTypeService productTypeService;

  /**
   * DataCacheApplication.
   */
  @Autowired
  private transient ProductCacheApplication cacheApplication;

  /**
   * Create Product.
   *
   * @param draft product draft
   * @param developerId the developer id
   * @return product view
   */
  @Transactional
  public ProductView create(ProductDraft draft, String developerId) {
    LOG.debug("Enter. developerId: {}, draft: {}.", developerId, draft);

    // 1. 检查名字是否重复
    productService.isExistName(developerId, draft.getName());

    // 2. 检查类型是否存在
    productTypeService.exists(draft.getProductTypeId());

    // 3. 生成实体对象
    Product product = ProductMapper.toModel(draft, developerId);

    product = productService.save(product);

    cacheApplication.deleteProducts(developerId);

    ProductView view = ProductMapper.toView(product);

    LOG.debug("Exit. productView: {}.", view);
    return view;
  }

  /**
   * 删除Product。
   *
   * @param id product id
   * @param developerId the developer id
   * @param version the version
   */
  public void delete(String id, String developerId, Integer version) {
    LOG.debug("Enter. id: {}, developerId: {}, version: {}.", id, developerId, version);

    Product product = productService.get(id);

//    checkForUpdateAndDelete(developerId, product, version);

    productService.delete(id);

    cacheApplication.deleteProducts(developerId);

//    restClient.deleteAllDataDefinition(developerId, product.getId());

    LOG.debug("Exit.");
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
//    LOG.debug("Enter: id: {}, version: {}, actions: {}.", id, version, actions);
//
//    Product valueInDb = productService.get(id);
//
//    checkForUpdateAndDelete(developerId, valueInDb, version);
//
//    actions.stream().forEach(action -> updaterService.handle(valueInDb, action));
//
//    Product product = productService.save(valueInDb);
//
//    cacheApplication.deleteProducts(developerId);
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
//    LOG.debug("Exit: updated product: {}", updatedProduct);
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
}

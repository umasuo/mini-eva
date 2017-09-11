package cn.eva.mini.application.rest;

import cn.eva.mini.application.dto.product.ProductTypeDraft;
import cn.eva.mini.application.dto.product.ProductTypeView;
import cn.eva.mini.application.service.ProductTypeApplication;
import cn.eva.mini.infra.router.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for ProductType.
 */
@CrossOrigin
@RestController
public class ProductTypeController {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeController.class);

  /**
   * ProductType application.
   */
  @Autowired
  private transient ProductTypeApplication productTypeApplication;


  /**
   * Create ProductType.
   *
   * @param productTypeDraft the product type draft
   * @return the product type view
   */
  @PostMapping(Router.ADMIN_PRODUCT_TYPE_ROOT)
  public ProductTypeView create(@RequestBody ProductTypeDraft productTypeDraft) {
    LOGGER.info("Enter. productTypeDraft: {}.", productTypeDraft);

    ProductTypeView result = productTypeApplication.create(productTypeDraft);

    LOGGER.info("Exit. new productType: {}.", result);
    return result;
  }

  /**
   * Delete ProductType by it's id.
   *
   * @param id the id
   * @param version the version
   */
  @DeleteMapping(Router.ADMIN_PRODUCT_TYPE_WITH_ID)
  public void delete(@PathVariable("id") String id, @RequestParam("version") Integer version) {
    LOGGER.info("Enter. product type id: {}, version: {}.", id, version);

    productTypeApplication.delete(id, version);

    LOGGER.info("Exit. delete done.");
  }


  /**
   * Update ProductType.
   *
   * @param id the id
   * @param updateRequest the update request
   * @return the product type view
   */
//  @PutMapping(Router.ADMIN_PRODUCT_TYPE_WITH_ID)
//  public ProductTypeView update(@PathVariable("id") String id,
//      @RequestBody @Valid UpdateRequest updateRequest) {
//    LOGGER.info("Enter. product type id: {}, updateRequest: {}.", id, updateRequest);
//
//    ProductTypeView result =
//        productTypeApplication.update(id, updateRequest.getVersion(), updateRequest.getActions());
//
//    LOGGER.trace("Updated productType: {}.", result);
//    LOGGER.info("Exit.");
//
//    return result;
//  }

  /**
   * Gets one ProductType by id.
   *
   * @param id the id
   * @return the one
   */
  @GetMapping(Router.ADMIN_PRODUCT_TYPE_WITH_ID)
  public ProductTypeView getOne(@PathVariable("id") String id) {
    LOGGER.info("Enter. product type id: {}.", id);

    ProductTypeView result = productTypeApplication.get(id);

    LOGGER.trace("ProductType: {}.", result);
    LOGGER.info("Exit.");

    return result;
  }

  /**
   * 查询所有的产品类型，用于新建产品时选择类型和对应的功能，数据。
   * 开放接口，开发者可以访问。
   * 该接口配置了两个path，分别用于developer-site和admin-site.
   */
  @GetMapping(value = {Router.PRODUCT_TYPE_ROOT, Router.ADMIN_PRODUCT_TYPE_ROOT})
  public List<ProductTypeView> getAll() {
    LOGGER.info("Enter.");

    List<ProductTypeView> result = productTypeApplication.getAll();

    LOGGER.info("Exit. productType size: {}.", result.size());

    return result;
  }
}

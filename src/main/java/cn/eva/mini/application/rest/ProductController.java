package cn.eva.mini.application.rest;

import cn.eva.mini.application.dto.product.ProductDraft;
import cn.eva.mini.application.dto.product.ProductView;
import cn.eva.mini.application.service.product.ProductApplication;
import cn.eva.mini.domain.service.ProductService;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

/**
 * Controller class for Product.
 */
@CrossOrigin
@RestController
public class ProductController {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

  /**
   * ProductApplication.
   */
  @Autowired
  private transient ProductApplication productApplication;

  /**
   * ProductApplication.
   */
  @Autowired
  private transient ProductService productService;

  /**
   * Create new product.
   *
   * @param developerId the developer id
   * @param draft       Product draft
   * @return product view
   */
  @PostMapping(Router.PRODUCT_ROOT)
  public ProductView create(@RequestHeader("developerId") String developerId,
                            @RequestBody @Valid ProductDraft draft) {
    LOGGER.info("Enter. developerId: {}, productDraft: {}.", developerId, draft);

    ProductView view = productApplication.create(draft, developerId);

    LOGGER.info("Exit. productView: {}.", view);

    return view;
  }

  /**
   * Delete a product by it's id.
   *
   * @param id          the id
   * @param developerId the developer id
   * @param version     the version
   */
  @DeleteMapping(Router.PRODUCT_WITH_ID)
  public void delete(@PathVariable("id") String id, @RequestHeader String developerId,
                     @RequestParam("version") Integer version) {
    LOGGER.info("Enter. id: {}, developerId: {}, version: {}.", id, developerId, version);

    productApplication.delete(id, developerId, version);

    LOGGER.info("Exit.");
  }

  /**
   * Update Product.
   *
   * @param id the Product id
   * @param developerId the developer id
   * @param updateRequest the update request
   * @return the ProductView
   */
//  @PutMapping(Router.PRODUCT_WITH_ID)
//  public ProductView update(@PathVariable("id") String id, @RequestHeader String developerId,
//                            @RequestBody @Valid ProductTypeUpdateRequest updateRequest) {
//    LOGGER.info("Enter. productTypeId: {}, updateRequest: {}, developerId: {}.",
//        id, updateRequest, developerId);
//
//    ProductView result = productApplication
//        .update(id, developerId, updateRequest.getVersion(), updateRequest.getActions());
//
//    LOGGER.trace("Updated product: {}.", result);
//    LOGGER.info("Exit.");
//
//    return result;
//  }

  /**
   * Get product by it's id.
   *
   * @param id          String
   * @param developerId the developer id
   * @return ProductView product view
   */
  @GetMapping(Router.PRODUCT_WITH_ID)
  public ProductView get(@PathVariable String id, @RequestHeader String developerId) {
    LOGGER.info("Enter. id: {}.", id);

    ProductView view = productApplication.get(id, developerId);

    LOGGER.info("Exit. view: {}.", view);
    return view;
  }

  /**
   * Get all developer's product by developer id.
   *
   * @param developerId String
   * @return list build product view
   */
  @GetMapping(Router.PRODUCT_ROOT)
  public List<ProductView> getByDeveloperId(@RequestHeader String developerId) {
    LOGGER.info("Enter. developerId: {}.", developerId);

    List<ProductView> views = productApplication.getAllByDeveloperId(developerId);

    LOGGER.info("Exit. viewsSize: {}.", views.size());
    LOGGER.trace("views: {}.", views);
    return views;
  }

  /**
   * Count products.
   *
   * @return the long
   */
  @GetMapping(value = Router.ADMIN_PRODUCT_COUNT)
  public Long countProducts() {
    LOGGER.info("Enter.");

    Long count = productService.countProducts();

    LOGGER.info("Exit. product countProducts: {}.", count);

    return count;
  }
}

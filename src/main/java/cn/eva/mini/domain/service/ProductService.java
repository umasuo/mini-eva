package cn.eva.mini.domain.service;

import cn.eva.mini.domain.entity.Product;
import cn.eva.mini.infra.exception.AlreadyExistException;
import cn.eva.mini.infra.exception.NotExistException;
import cn.eva.mini.infra.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Product.
 */
@Service
public class ProductService {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

  /**
   * ProductRepository.
   */
  @Autowired
  private transient ProductRepository repository;

  /**
   * 保存product.
   *
   * @param product the product
   * @return product product
   */
  public Product save(Product product) {
    LOGGER.debug("Enter. product: {}.", product);

    Product savedProduct = repository.save(product);

    LOGGER.debug("Exit. saved product: {}.", savedProduct);
    return savedProduct;
  }

  /**
   * Delete product by it's id.
   *
   * @param id the id
   */
  public void delete(String id) {
    LOGGER.debug("Enter. id: {}.", id);

    repository.delete(id);

    LOGGER.debug("Exit.");
  }

  /**
   * Gets by developer id.
   *
   * @param developerId the developer id
   * @return by developer id
   */
  public List<Product> getByDeveloperId(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    List<Product> products = repository.findAllByDeveloperId(developerId);

    LOGGER.debug("Exit. products size: {}.", products.size());

    return products;
  }

  /**
   * Get product.
   *
   * @param id the id
   * @return product product
   */
  public Product get(String id) {
    LOGGER.debug("Enter. id: {}.", id);

    Product product = repository.findOne(id);
    if (product == null) {
      throw new NotExistException("Product not exist, id: " + id);
    }

    LOGGER.debug("Exit. product: {}.", product);
    return product;
  }

  /**
   * Gets all open product.
   *
   * @param developerId the developer id
   * @return the all openable product
   */
  public List<Product> getAllOpenProduct(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    Product sample = new Product();
    sample.setDeveloperId(developerId);
    sample.setOpenable(true);

    Example<Product> exam = Example.of(sample);

    List<Product> products = repository.findAll(exam);

    LOGGER.debug("Exit. products size: {}.", products.size());

    return products;
  }

  /**
   * Is exist name in developer.
   *
   * @param developerId the developer id
   * @param name        the name
   * @return the boolean
   */
  public boolean isExistName(String developerId, String name) {
    LOGGER.debug("Enter. developerId: {}, name: {}.", developerId, name);

    Product sample = new Product();
    sample.setDeveloperId(developerId);
    sample.setName(name);

    Example<Product> example = Example.of(sample);

    boolean result = repository.exists(example);

    if (result) {
      LOGGER.debug("Product name: {} has existed in developer: {}.", name, developerId);
      throw new AlreadyExistException("Product name has existed");
    }

    LOGGER.debug("Exit. name: {} exist: {}.", name, result);

    return result;
  }

  /**
   * Count products.
   *
   * @return long
   */
  public Long countProducts() {
    LOGGER.debug("Enter.");

    Long count = repository.count();

    LOGGER.debug("Exit. product countProducts: {}.", count);

    return count;
  }
}

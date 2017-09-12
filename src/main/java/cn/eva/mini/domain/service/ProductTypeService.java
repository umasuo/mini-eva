package cn.eva.mini.domain.service;

import cn.eva.mini.domain.entity.ProductType;
import cn.eva.mini.infra.exception.NotExistException;
import cn.eva.mini.infra.repository.ProductTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for ProductType.
 */
@Service
public class ProductTypeService {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeService.class);

  /**
   * ProductTypeRepository.
   */
  @Autowired
  private transient ProductTypeRepository repository;

  /**
   * Save product type.
   *
   * @param productType the product type
   * @return product type
   */
  public ProductType save(ProductType productType) {
    LOGGER.debug("Enter. productType: {}", productType);

    ProductType newProductType = repository.save(productType);

    LOGGER.debug("Exit. new productType id: {}.", newProductType.getId());

    return newProductType;
  }

  /**
   * Delete by id.
   *
   * @param id the id
   */
  public void delete(String id) {
    LOGGER.debug("Enter. product type id: {}.", id);

    repository.delete(id);

    LOGGER.debug("Exit.");
  }

  /**
   * 查询所有的产品类型。
   *
   * @return all
   */
  public List<ProductType> getAll() {
    LOGGER.debug("Enter.");

    List<ProductType> productTypes = repository.findAll();

    LOGGER.debug("Exit. productType size: {}.", productTypes.size());

    return productTypes;
  }

  /**
   * 根据id查询产品类型
   *
   * @param id the id
   * @return by id
   */
  public ProductType getById(String id) {
    LOGGER.debug("Enter. id: {}.", id);

    ProductType result = repository.findOne(id);

    if (result == null) {
      LOGGER.debug("Can not find productType: {}.", id);
      throw new NotExistException("ProductType is not exist");
    }

    LOGGER.debug("Exit. productType: {}.", result);
    return result;
  }

  /**
   * Exists.
   *
   * @param id the id
   */
  public void exists(String id) {
    LOGGER.debug("Enter. id: {}.", id);

    boolean exists = repository.exists(id);

    if (!exists) {
      LOGGER.debug("Can not find productType: {}.", id);
      throw new NotExistException("ProductType is not exist");
    }

    LOGGER.debug("Exit. productType exist? {}.", exists);
  }
}

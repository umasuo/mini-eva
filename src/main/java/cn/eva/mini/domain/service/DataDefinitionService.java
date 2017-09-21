package cn.eva.mini.domain.service;

import cn.eva.mini.domain.entity.DeviceDataDefinition;
import cn.eva.mini.infra.exception.AlreadyExistException;
import cn.eva.mini.infra.exception.NotExistException;
import cn.eva.mini.infra.repository.DataDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DataDefinitionService.
 */
@Service
public class DataDefinitionService {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(DataDefinitionService.class);

  /**
   * Repository.
   */
  @Autowired
  private transient DataDefinitionRepository repository;

  /**
   * 判断dataId是否已经在developer＋product下存在。
   *
   * @param productTypeId   the product id
   * @param dataId      the data id
   */
  public void isExistDataId( String productTypeId, String dataId) {
    LOGGER.debug("Enter. productTypeId: {}, dataId: {}.", productTypeId, dataId);

    DeviceDataDefinition ex = new DeviceDataDefinition();
    ex.setDataId(dataId);
    ex.setProductTypeId(productTypeId);
    Example<DeviceDataDefinition> example = Example.of(ex);

    boolean exists = this.repository.exists(example);
    if (exists) {
      LOGGER.debug("DataId: {} has existed for product: {}.", dataId, productTypeId);
      throw new AlreadyExistException("Data Definition already exist for dataId: " + dataId);
    }

    LOGGER.debug("Exit. dataId is unique.");
  }

  /**
   * Is exist name in developer.
   *
   * @param productTypeId   the developer id
   * @param name        the name
   * @return the boolean
   */
  public void isExistName(String productTypeId, String name) {
    LOGGER.debug("Enter. productTypeId: {}, name: {}.", productTypeId, name);

    DeviceDataDefinition sample = new DeviceDataDefinition();
    sample.setProductTypeId(productTypeId);
    sample.setName(name);

    Example<DeviceDataDefinition> example = Example.of(sample);

    boolean exists = repository.exists(example);

    if (exists) {
      LOGGER.debug("Name: {} has existed in product: {}.", name, productTypeId);
      throw new AlreadyExistException("Name has existed");
    }

    LOGGER.debug("Exit. name is unique.");
  }

  /**
   * Save DeviceDataDefinition.
   *
   * @param dataDefinition the data definition
   * @return the data definition
   */
  public DeviceDataDefinition save(DeviceDataDefinition dataDefinition) {
    LOGGER.debug("Enter. dataDefinition: {}.", dataDefinition);

    DeviceDataDefinition result = repository.save(dataDefinition);

    LOGGER.debug("Exit. saved DeviceDataDefinition: {}.", result);

    return result;
  }

  /**
   * Save all list.
   *
   * @param dataDefinitions the data definitions
   * @return the list
   */
  public List<String> saveAll(List<DeviceDataDefinition> dataDefinitions) {
    LOGGER.debug("Enter. dataDefinitions size: {}.", dataDefinitions.size());

    List<DeviceDataDefinition> savedDataDefinitions = repository.save(dataDefinitions);


    List<String> dataDefinitionIds = savedDataDefinitions.stream()
      .map(DeviceDataDefinition::getId).collect(Collectors.toList());

    LOGGER.debug("Exit. dataDefinition ids: {}.", dataDefinitionIds);

    return dataDefinitionIds;
  }

  /**
   * Delete.
   *
   * @param id the id
   */
  public void delete(String id) {
    LOGGER.debug("Enter. id: {}.", id);

    repository.delete(id);

    LOGGER.debug("Exit.");
  }

  /**
   * Delete by product.
   *
   * @param productTypeId   the product id
   */
  public void deleteByProductType(String productTypeId) {
    LOGGER.debug("Enter. productTypeId: {}.", productTypeId);

    List<DeviceDataDefinition> dataDefinitions = getByProductTypeId(productTypeId);

    repository.delete(dataDefinitions);

    LOGGER.debug("Exit.");
  }

  /**
   * Get one from db.
   *
   * @param id the id
   * @return by id
   */
  public DeviceDataDefinition getById(String id) {
    LOGGER.debug("Enter. id: {}", id);

    DeviceDataDefinition valueInDb = this.repository.findOne(id);

    if (valueInDb == null) {
      LOGGER.debug("Can not find dataDefinition: {}.", id);
      throw new NotExistException("DataDefinition not exist.");
    }

    LOGGER.debug("Exit.");

    return valueInDb;
  }

  /**
   * Gets by product id.
   *
   * @param productTypeId   the product id
   * @return the by product id
   */
  public List<DeviceDataDefinition> getByProductTypeId( String productTypeId) {
    LOGGER.debug("Enter. productTypeId: {}.", productTypeId);

    DeviceDataDefinition sample = new DeviceDataDefinition();
    sample.setProductTypeId(productTypeId);

    Example<DeviceDataDefinition> example = Example.of(sample);

    List<DeviceDataDefinition> result = repository.findAll(example);

    LOGGER.debug("Exit. dataDefinition size: {}.", result.size());

    return result;
  }

  /**
   * Get all platform data definition.
   *
   * @return all
   */
  public List<DeviceDataDefinition> getAll() {
    LOGGER.debug("Enter.");

    List<DeviceDataDefinition> dataDefinitions = repository.findAll();

    LOGGER.debug("Exit. DeviceDataDefinition size: {}.", dataDefinitions.size());

    return dataDefinitions;
  }
}

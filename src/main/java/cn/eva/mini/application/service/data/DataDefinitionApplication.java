package cn.eva.mini.application.service.data;

import cn.eva.mini.application.dto.data.DataDefinitionDraft;
import cn.eva.mini.application.dto.data.DataDefinitionView;
import cn.eva.mini.application.dto.data.mapper.DataDefinitionMapper;
import cn.eva.mini.domain.entity.DeviceDataDefinition;
import cn.eva.mini.domain.service.DataDefinitionService;
import cn.eva.mini.infra.exception.NotExistException;
import cn.eva.mini.infra.util.SchemaValidator;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * DataDefinitionApplication.
 */
@Service
public class DataDefinitionApplication {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(DataDefinitionApplication.class);

  /**
   * The DataDefinitionService.
   */
  @Autowired
  private transient DataDefinitionService definitionService;

  /**
   * Cache application service.
   */
  @Autowired
  private transient DataCacheApplication cacheApplication;

  /**
   * The UpdateService.
   */
//  @Autowired
//  private transient UpdaterService updaterService;

  /**
   * Create DeviceDataDefinition.
   *
   * @param draft       the draft
   * @return the data definition view
   */
  public DataDefinitionView create(DataDefinitionDraft draft) {
    LOGGER.debug("Enter. draft: {}.", draft);

    SchemaValidator.validate(draft.getDataSchema());

    definitionService.isExistName(draft.getProductTypeId(), draft.getName());

    definitionService.isExistDataId(draft.getProductTypeId(), draft.getDataId());

    DeviceDataDefinition definition =
      definitionService.save(DataDefinitionMapper.toModel(draft));

    cacheApplication.deleteDataDefinition( draft.getProductTypeId());

    DataDefinitionView view = DataDefinitionMapper.toView(definition);

    LOGGER.debug("Exit. view: {}.", view);

    return view;
  }

  /**
   * Update DeviceDataDefinition.
   *
   * @param id          the id
   * @param developerId the developer id
   * @param version     the version
   * @param actions     the actions
   * @return updated DataDefinitionView
   */
//  public DataDefinitionView update(String id, String developerId, Integer version,
//                                   List<UpdateAction> actions) {
//    LOGGER.debug("Enter. id: {}, version: {}, developerId:{}, actions: {}.",
//        id, version, developerId, actions);
//
//    DeviceDataDefinition definition = definitionService.getById(id);
//
//    DefinitionValidator.validateDeveloper(developerId, definition.getDeveloperId(), id);
//
////    VersionValidator.validate(version, definition.getVersion());
//
//    actions.stream().forEach(action -> updaterService.handle(definition, action));
//
//    DeviceDataDefinition updatedDefinition = definitionService.save(definition);
//
//    cacheApplication.deleteDataDefinition(developerId, definition.getProductTypeId());
//
//    DataDefinitionView result = DataDefinitionMapper.toView(updatedDefinition);
//
//    LOGGER.trace("Updated DeviceDataDefinition: {}.", result);
//    LOGGER.debug("Exit.");
//
//    return result;
//  }

  /**
   * Delete.
   *
   * @param productId   the product id
   */
  public void delete(String productId) {
    LOGGER.debug("Enter. productTypeId: {}.", productId);

    definitionService.deleteByProductType(productId);
    // TODO: 17/9/21 check if this product type has products

    cacheApplication.deleteDataDefinition(productId);

    LOGGER.debug("Exit.");
  }

  /**
   * 获取productId对应的所有dataDefinition.
   *
   * @param productTypeId the product id
   * @return dataDefinition list
   */
  public List<DataDefinitionView> getByProductTypeId( String productTypeId) {
    LOGGER.debug("Enter. productTypeId: {}.", productTypeId);

    List<DeviceDataDefinition> dataDefinitions = cacheApplication.getProductDataDefinition(productTypeId);

    if (CollectionUtils.isEmpty(dataDefinitions)) {
      dataDefinitions = definitionService.getByProductTypeId(productTypeId);
      cacheApplication.cacheProductDataDefinition(productTypeId, dataDefinitions);
    }

    List<DataDefinitionView> result = DataDefinitionMapper.toView(dataDefinitions);

    LOGGER.debug("Exit. dataDefinition size: {}.", result.size());

    return result;
  }

  /**
   * Gets by product ids.
   *
   * @param productTypeIds  the product ids
   * @return the by product ids
   */
  public Map<String, List<DataDefinitionView>> getByProductTypeIds(List<String> productTypeIds) {
    LOGGER.debug("Enter. productIds: {}.", productTypeIds);

    // TODO: 17/7/12 简单粗暴的实现方式，待优化成批量处理
    Map<String, List<DataDefinitionView>> result = Maps.newHashMap();

    Consumer<String> consumer = id -> result.put(id, getByProductTypeId(id));

    productTypeIds.stream().forEach(consumer);

    return result;
  }

  /**
   * Get data definition view.
   *
   * @param productTypeId   the product id
   * @param id          the id
   * @return the data definition view
   */
  public DataDefinitionView get(String productTypeId, String id) {
    LOGGER.debug("Enter. developerId: {}, productTypeId: {}, id: {}.", productTypeId, id);

    DeviceDataDefinition dataDefinition =
      cacheApplication.getProductDataDefinition(productTypeId, id);

    if (dataDefinition == null) {
      LOGGER.debug("Cache fail, query dataDefinition from database and cache.");

      List<DeviceDataDefinition> dataDefinitions =
        definitionService.getByProductTypeId(productTypeId);

      cacheApplication.cacheProductDataDefinition(productTypeId, dataDefinitions);

      dataDefinition =
        dataDefinitions.stream().filter(data -> id.equals(data.getId())).findAny().orElse(null);

      if (dataDefinition == null) {
        LOGGER.debug("DataDefinition: {} not exist.", id);
        throw new NotExistException("DataDefinition not exist");
      }
    }

    DataDefinitionView result = DataDefinitionMapper.toView(dataDefinition);

    LOGGER.debug("Exit. dataDefinition: {}.", result);

    return result;
  }

}

package cn.eva.mini.application.rest;

import cn.eva.mini.application.dto.data.DataDefinitionDraft;
import cn.eva.mini.application.dto.data.DataDefinitionView;
import cn.eva.mini.application.service.data.DataDefinitionApplication;
import cn.eva.mini.domain.service.DataDefinitionService;
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
import java.util.Map;

import javax.validation.Valid;

/**
 * DataDefinitionController.
 */
@RestController
@CrossOrigin
public class DataDefinitionController {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(DataDefinitionController.class);

  /**
   * Data definition service.
   */
  @Autowired
  private transient DataDefinitionService definitionService;

  /**
   * Data definition app.
   */
  @Autowired
  private transient DataDefinitionApplication definitionApplication;

  /**
   * 新建数据定义.
   *
   * @param definitionDraft 数据定义draft
   * @param developerId     开发者ID
   * @param definitionDraft the definition draft
   * @return the data definition view
   */
  @PostMapping(value = Router.DATA_DEFINITION_ROOT)
  public DataDefinitionView create(@RequestBody @Valid DataDefinitionDraft definitionDraft,
                                   @RequestHeader String developerId) {
    LOGGER.info("Enter. definitionDraft: {}, developerId: {}.", definitionDraft, developerId);

    DataDefinitionView view = definitionApplication.create(definitionDraft);

    LOGGER.info("Exit. dataDefinition: {}", view);
    return view;
  }

  /**
   * Update DeviceDataDefinition.
   *
   * @param id            the DeviceDataDefinition id
   * @param developerId   the Developer id
   * @param updateRequest the ProductTypeUpdateRequest
   * @return updated DeviceDataDefinition
   */
//  @PutMapping(value = Router.DATA_DEFINITION_WITH_ID)
//  public DataDefinitionView update(@PathVariable String id,
//                                   @RequestHeader String developerId,
//                                   @RequestBody @Valid ProductTypeUpdateRequest updateRequest) {
//    LOGGER.info("Enter. dataDefinitionId: {}, updateRequest: {}, developerId: {}.",
//        id, updateRequest, developerId);
//
//    DataDefinitionView result =
//        definitionApplication.update(id, developerId, updateRequest.getVersion(), updateRequest
//            .getActions());
//
//    LOGGER.trace("Updated definition: {}.", result);
//    LOGGER.info("Exit.");
//
//    return result;
//  }

  /**
   * Delete.
   *
   * @param id
   * @param developerId
   * @param productTypeId
   */
//  @DeleteMapping(value = Router.DATA_DEFINITION_WITH_ID)
//  public void delete(@PathVariable String id,
//                     @RequestHeader String developerId, @RequestParam String productTypeId) {
//    LOGGER.info("Enter. id: {}, developerId: {}, productTypeId: {}.", id, developerId, productTypeId);
//
//    definitionApplication.delete(developerId, productTypeId, id);
//
//    LOGGER.info("Exit.");
//  }

  /**
   * Delete.
   *
   * @param developerId
   * @param productId
   */
  @DeleteMapping(value = Router.DATA_DEFINITION_ROOT)
  public void delete(@RequestHeader String developerId, @RequestParam String productId) {
    LOGGER.debug("Enter. developerId: {}, productTypeId: {}.", developerId, productId);

    definitionApplication.delete(productId);

    LOGGER.info("Exit.");
  }

  /**
   * Get by product id.
   *
   * @param developerId
   * @param productId
   * @return
   */
  @GetMapping(value = Router.DATA_DEFINITION_ROOT, params = {"productTypeId"})
  public List<DataDefinitionView> getByProductId(@RequestHeader String developerId,
                                                 @RequestParam String productId) {
    LOGGER.info("Enter. developerId: {}, productTypeId: {}.", developerId, productId);

    List<DataDefinitionView> result = definitionApplication.getByProductTypeId(productId);

    LOGGER.info("Exit. dataDefinition size: {}.", result.size());

    return result;
  }

  /**
   * Get by product id list.
   *
   * @param developerId
   * @param productIds
   * @return
   */
  @GetMapping(value = Router.DATA_DEFINITION_ROOT, params = {"productIds"})
  public Map<String, List<DataDefinitionView>> getByProductIdList(@RequestHeader String developerId,
                                                                  @RequestParam List<String> productIds) {
    LOGGER.info("Enter. developerId: {}, productIds: {}.", developerId, productIds);

    Map<String, List<DataDefinitionView>> result = definitionApplication.getByProductTypeIds(productIds);

    LOGGER.info("Exit.");

    return result;
  }

  /**
   * 获取单个Data definition。
   * 内部接口，不对外开放。
   *
   * @param id
   * @return
   */
  @GetMapping(value = Router.DATA_DEFINITION_WITH_ID)
  public DataDefinitionView get(@RequestHeader String developerId,
                                @PathVariable("id") String id,
                                @RequestParam String productId) {
    LOGGER.info("Enter. developerId: {}, productTypeId: {}, id: {}.", developerId, productId, id);

    DataDefinitionView result = definitionApplication.get(productId, id);

    LOGGER.info("Exit. dataDefinition: {}.", result);

    return result;
  }

}

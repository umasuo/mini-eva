package cn.eva.mini.application.service.data;

import cn.eva.mini.application.dto.data.DeviceDataDraft;
import cn.eva.mini.application.dto.data.DeviceDataView;
import cn.eva.mini.application.dto.data.mapper.DeviceDataMapper;
import cn.eva.mini.domain.entity.DeviceData;
import cn.eva.mini.domain.service.DeviceDataService;
import cn.eva.mini.infra.util.TimeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data create application.
 */
@Service
public class DataCreateApplication {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(DeviceDataService.class);

  /**
   * Device data service.
   */
  @Autowired
  private transient DeviceDataService deviceDataService;

  /**
   * 所有设备数据存储的唯一接口，此接口的压力会非常大，尽量优化.
   * 所有数据均由设备产生.
   *
   * @param dataDraft
   * @return
   */
  public DeviceDataView create(DeviceDataDraft dataDraft, String developerId, String userId) {
    LOGGER.debug("Enter. dataDraft: {}", dataDraft);

    // Check if device exist.
//    Device device = restClient.getDevice(dataDraft.getDeviceId(), developerId);
//    if (device == null) {
//      LOGGER.debug("Device: {} not exist.", dataDraft.getDeviceId());
//      throw new ParametersException("Device not exist, deviceId: " + dataDraft.getDeviceId());
//    }
//
//    // Check if user is bound to the device
//    if (StringUtils.isNotBlank(userId) &&
//        !userId.equals(device.getOwnerId())) {
//      LOGGER.debug("User: {} is not bound to the device: {}.", userId, device.getId());
//      throw new ParametersException("User: " + userId +
//          " not bound to the device: " + device.getId());
//    }
//
//    // Check json schema
//    DataDefinition dataDefinition = restClient.getDataDefinition(
//        dataDraft.getDataId(), developerId, dataDraft.getDeviceDefinitionId());
//
//    try {
//      JsonSchema schema = JsonSchemaFactory.byDefault().getJsonSchema(dataDefinition
//          .getJsonSchema());
//      schema.validate(dataDraft.getData());
//    } catch (ProcessingException ex) {
//      LOGGER.debug("Data is not in correct format, dataDraft: {}.", dataDraft);
//      throw new ParametersException("Data is not in correct format.");
//    }
//
//    DeviceData data = DeviceDataMapper.toView(dataDraft, developerId, userId);
//    DeviceData dataSaved = deviceDataService.create(data);
//
//    //todo 原始数据已经完成存储，发出相关消息，然后开始进行数据的处理
//
//    LOGGER.debug("Exit. dataSaved: {}", dataSaved);
//    return DeviceDataMapper.toView(dataSaved);
    return null;
  }

  /**
   * 创建list数据.
   *
   * @param drafts      draft 列表
   * @param developerId 开发者ID
   * @param userId      用户ID
   * @return
   */
  public List<DeviceDataView> createList(List<DeviceDataDraft> drafts, String developerId, String
    userId) {
    LOGGER.debug("Enter. ");

    List<DeviceDataView> views = drafts.stream().map(
      dataDraft -> create(dataDraft, developerId, userId)
    ).collect(Collectors.toList());

    LOGGER.debug("Exit. ");
    return views;
  }

  /**
   * 获取某个用户的某个设备的某种类型的数据在某一段时间的值.
   * 时间跨度不能够太大，否则会导致数据量过大而程序奔溃，用户拉取数据，限制在一次只能够拉取一周的数据.
   *
   * @param developerId 开发者ID
   * @param userId      用户ID
   * @param dataId      数据ID，开发者定义
   * @param deviceId    设备终端ID
   * @param start       开始时间
   * @param end         结束时间
   * @return 数据列表
   */
  public List<DeviceDataView> get(String developerId,
                                  String userId,
                                  String dataId,
                                  String deviceId,
                                  long start,
                                  long end) {
    LOGGER.debug("Enter. developerId: {}, userId: {}, dataId: {}, deviceId: {}, start: {}, end: {}.", developerId, userId, dataId, deviceId, start, end);

    TimeValidator.validatePeriod(start, end);

    List<DeviceData> dataList = deviceDataService.get(developerId, userId, dataId, deviceId,
      start, end);

    List<DeviceDataView> viewList = DeviceDataMapper.toView(dataList);

    LOGGER.debug("Exit. dataSize: {}.", viewList.size());
    LOGGER.trace("Exit. data: {}.", viewList);
    return viewList;
  }
}

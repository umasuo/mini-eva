package cn.eva.mini.domain.service;

import cn.eva.mini.domain.entity.DeviceData;
import cn.eva.mini.infra.repository.DeviceDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DeviceDataService.
 */
@Service
public class DeviceDataService {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(DeviceDataService.class);

  /**
   * Repository.
   */
  @Autowired
  private transient DeviceDataRepository repository;

  /**
   * create one device data.
   *
   * @param data
   * @return
   */
  public DeviceData create(DeviceData data) {
    LOGGER.debug("Enter. {}", data);

    DeviceData saved = repository.save(data);

    LOGGER.debug("Exit. saved: {}.", saved);
    return saved;
  }

  /**
   * Get device data.
   *
   * @param developerId
   * @param userId
   * @param dataId
   * @param deviceId
   * @param start
   * @param end
   * @return
   */
  public List<DeviceData> get(String developerId,
                              String userId,
                              String dataId,
                              String deviceId,
                              long start,
                              long end) {
    LOGGER.debug("Enter. developerId: {}, userId: {}, dataId: {}, deviceId: {}, start: {}, end: {}.",
      developerId, userId, dataId, deviceId, start, end);
    List<DeviceData> dataList = repository.query(developerId, userId, dataId, deviceId,
      start, end);

    LOGGER.debug("Exit. dataSize: {}.", dataList.size());
    LOGGER.trace("Exit. data: {}.", dataList);
    return dataList;
  }

}

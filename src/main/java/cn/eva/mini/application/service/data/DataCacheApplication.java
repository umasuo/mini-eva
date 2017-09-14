package cn.eva.mini.application.service.data;

import cn.eva.mini.application.dto.data.mapper.DataDefinitionMapper;
import cn.eva.mini.domain.entity.DeviceDataDefinition;
import cn.eva.mini.infra.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * DataCacheApplication.
 */
@Service
public class DataCacheApplication {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DataCacheApplication.class);

  /**
   * Redis template.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * 根据developerId和productId获取对应的DeviceDataDefinition列表。
   *
   * @param developerId the developerId
   * @param productId the productId
   * @return DeviceDataDefinition list
   */
  public List<DeviceDataDefinition> getProductDataDefinition(String developerId, String productId) {

    LOGGER.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    String key = String.format(RedisUtils.DEVICE_DEFINITION_FORMAT, developerId, productId);

    List<DeviceDataDefinition> result = (List<DeviceDataDefinition>)
        redisTemplate.opsForHash().values(key);

    LOGGER.debug("Exit. dataDefinition size: {}.", result.size());
    return result;
  }

  /**
   * 根据developerId，productId，id获取对应的DeviceDataDefinition。
   *
   * @param developerId the
   * @param productId the product id
   * @param id the id
   * @return product data definition
   */
  public DeviceDataDefinition getProductDataDefinition(String developerId, String productId,
      String id) {
    LOGGER.debug("Enter. developerId: {}, productId: {}, id: {}.", developerId, productId, id);

    String key = String.format(RedisUtils.DEVICE_DEFINITION_FORMAT, developerId, productId);

    DeviceDataDefinition dataDefinition =
        (DeviceDataDefinition) redisTemplate.opsForHash().get(key, id);

    LOGGER.debug("Exit. dataDefinition: {}.", dataDefinition);

    return dataDefinition;
  }


  /**
   * 缓存某一个产品的数据定义.
   *
   * @param developerId the developer id
   * @param productId the product id
   * @param dataDefinitions the data definitions
   */
  public void cacheProductDataDefinition(String developerId, String productId,
      List<DeviceDataDefinition> dataDefinitions) {
    LOGGER.debug("Enter. developerId: {}, productId: {}, dataDefinition size: {}.",
        developerId, productId, dataDefinitions.size());

    String key = String.format(RedisUtils.DEVICE_DEFINITION_FORMAT, developerId, productId);

    Map<String, DeviceDataDefinition> entityMap = DataDefinitionMapper.toModelMap(dataDefinitions);

    redisTemplate.opsForHash().putAll(key, entityMap);

    LOGGER.debug("Exit.");
  }

  /**
   * 删除某一个产品的数据定义.
   *
   * @param developerId the developer id
   * @param productId the product id
   */
  public void deleteProductDataDefinition(String developerId, String productId) {
    LOGGER.debug("Enter. developerId: {}, productId: {}, dataDefinition size: {}.",
        developerId, productId);

    String key = String.format(RedisUtils.DEVICE_DEFINITION_FORMAT, developerId, productId);

    redisTemplate.delete(key);

    LOGGER.debug("Exit.");
  }
}

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
   * 根据productId获取对应的DeviceDataDefinition列表。
   *
   * @param ProductTypeId the product type id
   * @return DeviceDataDefinition list
   */
  public List<DeviceDataDefinition> getProductDataDefinition(String ProductTypeId) {

    LOGGER.debug("Enter. developerId: {}, productTypeId: {}.", ProductTypeId);

    String key = String.format(RedisUtils.DATA_DEFINITION_FORMAT, ProductTypeId);

    List<DeviceDataDefinition> result = (List<DeviceDataDefinition>) redisTemplate.opsForHash().values(key);

    LOGGER.debug("Exit. dataDefinition size: {}.", result.size());
    return result;
  }

  /**
   * 根据productId，dataDefinitionId获取对应的DeviceDataDefinition。
   *
   * @param productTypeId the product id
   * @param id            the id
   * @return product data definition
   */
  public DeviceDataDefinition getProductDataDefinition(String productTypeId, String id) {
    LOGGER.debug("Enter. productTypeId: {}, id: {}.", productTypeId, id);

    String key = String.format(RedisUtils.DATA_DEFINITION_FORMAT, productTypeId);

    DeviceDataDefinition dataDefinition = (DeviceDataDefinition) redisTemplate.opsForHash().get(key, id);

    LOGGER.debug("Exit. dataDefinition: {}.", dataDefinition);

    return dataDefinition;
  }


  /**
   * 缓存某一个产品的数据定义.
   *
   * @param productTypeId   the product id
   * @param dataDefinitions the data definitions
   */
  public void cacheProductDataDefinition(String productTypeId,
                                         List<DeviceDataDefinition> dataDefinitions) {
    LOGGER.debug("Enter. productTypeId: {}, dataDefinition size: {}.", productTypeId, dataDefinitions.size());

    String key = String.format(RedisUtils.DATA_DEFINITION_FORMAT, productTypeId);

    Map<String, DeviceDataDefinition> entityMap = DataDefinitionMapper.toModelMap(dataDefinitions);

    redisTemplate.opsForHash().putAll(key, entityMap);

    LOGGER.debug("Exit.");
  }

  /**
   * 删除某一个产品的数据定义.
   *
   * @param productTypeId the product id
   */
  public void deleteDataDefinition(String productTypeId) {
    LOGGER.debug("Enter. productTypeId: {}, dataDefinition size: {}.", productTypeId);

    String key = String.format(RedisUtils.DATA_DEFINITION_FORMAT, productTypeId);

    redisTemplate.delete(key);

    LOGGER.debug("Exit.");
  }


}

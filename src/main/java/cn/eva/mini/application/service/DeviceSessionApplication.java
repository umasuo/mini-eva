package cn.eva.mini.application.service;

import cn.eva.mini.application.dto.device.DeviceSession;
import cn.eva.mini.application.dto.device.DeviceView;
import cn.eva.mini.application.dto.device.mapper.DeviceMapper;
import cn.eva.mini.domain.entity.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Session application.
 */
@Service
public class DeviceSessionApplication {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSessionApplication.class);

  /**
   * Device prefix.
   */
  public static final String DEVICE_KEY = "device:";
  /**
   * Session key.
   */
  public static final String SESSION_KEY = "session";

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * Get session
   *
   * @param deviceId
   * @return
   */
  public DeviceSession getSession(String deviceId) {
    LOGGER.debug("Enter. deviceId:{}.", deviceId);
    String developerId = (String) redisTemplate.boundValueOps(DEVICE_KEY + ":" + deviceId).get();
    String deviceKey = DEVICE_KEY + developerId + ":" + deviceId;
    return (DeviceSession) redisTemplate.boundHashOps(deviceKey).get(SESSION_KEY);
  }

  /**
   * 更新device session.
   *
   * @param device Device
   */
  public void updateSession(Device device) {
    LOGGER.debug("Enter. device:{}.", device);
    String deviceId = device.getDeviceId();
    String developerId = device.getDeveloperId();
    String deviceKey = DEVICE_KEY + developerId + ":" + deviceId;

    DeviceSession session = getOrCreate(deviceKey);
    session.setLastUpdateTime(System.currentTimeMillis());
    //update session
    DeviceView view = DeviceMapper.toView(device);
    session.setDevice(view);
    redisTemplate.boundHashOps(deviceKey).put(SESSION_KEY, session);
    redisTemplate.boundValueOps(DEVICE_KEY + ":" + deviceId).set(developerId);

    // TODO: 17/7/20 顺便更新设备的MQTT值
  }

  /**
   * 清楚设备的session.
   *
   * @param developerId
   * @param deviceId
   */
  public void clearSession(String developerId, String deviceId) {
    // clear session
    String deviceKey = DEVICE_KEY + developerId + ":" + deviceId;
    redisTemplate.boundHashOps(deviceKey).delete(SESSION_KEY);
  }

  /**
   * 获取或创建新的设备session.
   *
   * @param deviceKey
   * @return
   */
  private DeviceSession getOrCreate(String deviceKey) {
    //get session or create if not exists
    DeviceSession session = (DeviceSession) redisTemplate.boundHashOps(deviceKey).get(SESSION_KEY);
    if (session == null) {
      session = new DeviceSession();
    }
    return session;
  }
}

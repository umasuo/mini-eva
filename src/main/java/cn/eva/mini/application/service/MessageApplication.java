package cn.eva.mini.application.service;

import cn.eva.mini.infra.config.AppConfig;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

/**
 * MessageApplication.
 */
@Service
public class MessageApplication implements CommandLineRunner {
  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MessageApplication.class);

  /**
   * MQTT connection.
   */
  private transient BlockingConnection connection;

  /**
   * MQTT user name cache prefix.
   */
  private static final String USERNAME_PREFIX = "mqtt_user:";

  /**
   * Device topic's prefix for device to pub.
   */
  private static final String DEVICE_TOPIC_PUB_PREFIX = "device/pub/";

  /**
   * Data message handler.
   */
  private transient DataMessageHandler deviceMessageHandler;


  /**
   * 初始化和message broker的连接.
   *
   * @param appConfig 系统配置
   */
  @Autowired
  public MessageApplication(StringRedisTemplate redisTemplate,
                            DataMessageHandler deviceMessageHandler,
                            AppConfig appConfig) {
    LOGGER.info("Init message client.");

    LOGGER.info("Message broker config: {}.", appConfig);

    this.deviceMessageHandler = deviceMessageHandler;

    redisTemplate.boundHashOps(USERNAME_PREFIX + appConfig.getUsername()).put("password",
        appConfig.getPassword());
    LOGGER.info("Connect to redis.");

    MQTT mqtt = new MQTT();
    mqtt.setUserName(appConfig.getUsername());
    mqtt.setPassword(appConfig.getPassword());
    connection = mqtt.blockingConnection();
    try {
      mqtt.setHost(appConfig.getMsgBrokerHost(), appConfig.getMsgBrokerPort());

      connection = mqtt.blockingConnection();

      connection.connect();
      LOGGER.info("Connect to message broker: " + appConfig.getMsgBrokerHost());
    } catch (Exception e) {
      LOGGER.error("Connect message broker failed.", e);
    }
  }

  /**
   * 发布消息.
   *
   * @param topic   topic，如果是设备的topic，则为topicID
   * @param payload 内容
   * @param qos     消息发送等级（0，1，2）
   * @param retain  是否保持在broker上
   */
  public void publish(final String topic, final byte[] payload, final QoS qos, final boolean
      retain) {
    LOGGER.debug("Enter. topic: {}, payload: {}, qos: {}, retain: {}.", topic,
        new String(payload, Charset.forName("UTF-8")), qos, retain);
    try {
      connection.publish(topic, payload, qos, retain);
    } catch (Exception e) {
      LOGGER.error("publish message failed.", e);
    }
  }

  /**
   * Service auto start.
   *
   * @param args
   * @throws Exception
   */
  @Override
  public void run(String... args) throws Exception {
    LOGGER.info("start process data.");
    while (true) {
      Message message = connection.receive();
      if (message != null) {
        String topic = message.getTopic();//从这里可以获得deviceID，
        String deviceId = topic.substring(DEVICE_TOPIC_PUB_PREFIX.length() - 1);

        boolean handlerResult = deviceMessageHandler.handler(deviceId, message.getPayload());
        if (handlerResult) {
          message.ack();
        }
      }
    }
  }
}

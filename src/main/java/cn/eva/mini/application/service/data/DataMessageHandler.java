package cn.eva.mini.application.service.data;

import org.springframework.stereotype.Service;

/**
 * Device message handler, used to handler messages that device send to cloud.
 */
@Service
public class DataMessageHandler {

//  private static final Logger logger = LoggerFactory.getLogger(DataMessageHandler.class);

  /**
   * 这里处理与数据相关的消息。例如上传数据等.
   *
   * @param deviceId deviceId
   * @param payload  the real content
   * @return handler result
   */
  public boolean handler(String deviceId, byte[] payload) {
    //1 通过deviceId 获取其public key
    //2 通过public key解密content
    //3 根据格式解析content
    //4 执行对应的服务
    //5 正确处理之后，返回true，否则返回false，以供再次处理消息

    return true;
  }
}

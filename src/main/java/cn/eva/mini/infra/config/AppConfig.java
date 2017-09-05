package cn.eva.mini.infra.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * App config.
 */
@Configuration
@Data
public class AppConfig {

  /**
   * message broker's host.
   */
  @Value("${message.broker.host:127.0.0.1}")
  public String msgBrokerHost;

  /**
   * message broker's port
   */
  @Value("${message.broker.port:1883}")
  public int msgBrokerPort;

  /**
   * super user's username for message broker.
   */
  @Value("${message.broker.username:umasuo}")
  public String username;

  /**
   * super user's password for message broker.
   */
  @Value("${message.broker.password:password}")
  public String password;
}

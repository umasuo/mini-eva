package cn.eva.mini.application.service;

import cn.eva.mini.application.dto.common.mapper.MailMessageMapper;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Mail sender.
 */
@Service(value = "mailSender")
public class MailSender {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MailSender.class);

  /**
   * Aliyun mail sender.
   */
  @Autowired
  private transient IAcsClient emailClient;

  /**
   * Send an email.
   */
  @Async
  public void send(String email, String subject, String message) {
    LOGGER.debug("Enter. email:{}, subject: {}.", email, subject);

    SingleSendMailRequest mailMessage = MailMessageMapper.build(email, subject, message);

    try {
      emailClient.getAcsResponse(mailMessage);
    } catch (ServerException ex) {
      LOGGER.debug("Something wrong when sending email.", ex);
    } catch (ClientException ex) {
      LOGGER.debug("Something wrong when sending email.", ex);
    }

    LOGGER.debug("Exit.");
  }
}

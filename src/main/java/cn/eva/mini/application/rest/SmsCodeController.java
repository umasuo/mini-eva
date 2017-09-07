package cn.eva.mini.application.rest;

import cn.eva.mini.application.service.SmsApplication;
import cn.eva.mini.infra.router.Router;
import com.yunpian.sdk.YunpianException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type sms code controller.
 */
@RestController
public class SmsCodeController {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SmsCodeController.class);

  /**
   * The sms service.
   */
  @Autowired
  private transient SmsApplication smsService;

  /**
   * Request validation code.
   *
   * @param phoneNumber the phone number
   */
  @PostMapping(Router.VALIDATION_CODE)
  public void getValidationCode(@RequestParam(Router.PHONE_NUMBER) String phoneNumber) {
    LOGGER.info("Enter. phoneNumber: {}.", phoneNumber);

    try {
      smsService.sendValidationCode(phoneNumber);
    } catch (YunpianException ex) {
      LOGGER.warn("Fail to send validation code.", ex);
    }

    LOGGER.info("Exit");
  }
}

package cn.eva.mini.application.service;

import cn.eva.mini.infra.exception.AlreadyExistException;
import cn.eva.mini.infra.exception.NotExistException;
import cn.eva.mini.infra.exception.ParametersException;
import cn.eva.mini.infra.util.RedisUtils;
import cn.eva.mini.infra.util.SmsCodeGenerator;
import cn.eva.mini.infra.util.SmsUrlUtils;
import com.yunpian.sdk.YunpianException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.yunpian.sdk.util.HttpUtil.post;

/**
 * The type Sms service.
 */
@Service
public class SmsApplication {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SmsApplication.class);

  /**
   * Validation code expire time;
   */
  private static final long EXPIRE_TIME = 10 * 60L;


  /**
   * Api key for yunpian.
   */
  @Value("${sms.yunpian.key}")
  private String yunpianApiKey;

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * Send validation code.
   *
   * @param phoneNumber the phone number
   */
  public void sendValidationCode(String phoneNumber) throws YunpianException {
    LOGGER.debug("Enter. phoneNumber: {}.", phoneNumber);

    String code = SmsCodeGenerator.generate();

    String key = String.format(RedisUtils.PHONE_KEY_FORMAT, phoneNumber);

    validateExistPhone(key);

    String codeKey = String.format(RedisUtils.PHONE_CODE_KEY_FORMAT, phoneNumber, code);

    this.sendValidationCode(code, phoneNumber);

    redisTemplate.opsForValue().set(codeKey, code, EXPIRE_TIME, TimeUnit.SECONDS);
    redisTemplate.opsForValue().set(key, "", 60, TimeUnit.SECONDS);

    LOGGER.debug("Exit.");
  }


  /**
   * 检查code是否正确.
   *
   * @param phone
   * @param smsCode
   * @return
   */
  public void validateCode(String phone, String smsCode) {

    String key = String.format(RedisUtils.PHONE_CODE_KEY_FORMAT, phone, smsCode);
    String cachedCode = (String) redisTemplate.opsForValue().get(key);
    if (StringUtils.isBlank(cachedCode)) {
      LOGGER.debug("Can not find validation code by phone: {}.", phone);
      throw new NotExistException("Validation code not exist.");
    }

    if (!cachedCode.equals(smsCode)) {
      LOGGER.debug("Validation code not match. request code: {}, basic code: {}.",
          smsCode, cachedCode);
      throw new ParametersException("Validation code not match");
    }

    redisTemplate.delete(key);
  }

  /**
   * Validate if this code exist.
   *
   * @param key the phone key in redis
   */
  private void validateExistPhone(String key) {
    String existValidationCode = (String) redisTemplate.opsForValue().get(key);
    if (existValidationCode != null) {
      LOGGER.debug("This phone has an exist validation code: {}.", existValidationCode);
      throw new AlreadyExistException("ValidationCode already exist");
    }
  }


  /**
   * Send Validation code.
   *
   * @param validationCode the validation code
   * @param phoneNumber    the phone number
   */
  @Async
  public String sendValidationCode(String validationCode, String phoneNumber)
    throws YunpianException {
    Map<String, String> params = new ConcurrentHashMap<>();

    params.put("apikey", yunpianApiKey);
    params.put("text", createSmsText(validationCode));
    params.put("mobile", phoneNumber);

    return post(SmsUrlUtils.URI_SEND_SMS, params);
  }

  /**
   * Create Sms text.
   *
   * @param validationCode validation code.
   * @return string
   */
  private String createSmsText(String validationCode) {
    return String.format("【伊娃App】您的验证码是%s。如非本人操作，请忽略本短信", validationCode);
  }
}

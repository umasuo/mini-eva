package cn.eva.mini.infra.util;

import cn.eva.mini.infra.exception.ParametersException;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.annotation.PostConstruct;

/**
 * Builder email content.
 */
@Component
public class MailContentBuilder {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MailContentBuilder.class);

  /**
   * Template string for verify developer.
   */
  private transient String verifyTemplate;

  /**
   * Template string for reset developer password.
   */
  private transient String resetTemplate;

  /**
   * Load email template into String after construct.
   */
  @PostConstruct
  public void loadEmailTemplate() {
    LOGGER.debug("Enter.");
    URL verifyResource = Resources.getResource("VerifyTemplate.html");
    URL resetResource = Resources.getResource("ResetTemplate.html");
    try {
      verifyTemplate = Resources.toString(verifyResource, Charsets.UTF_8);
      resetTemplate = Resources.toString(resetResource, Charsets.UTF_8);
    } catch (IOException e) {
      LOGGER.debug("Something wrong when read email template.", e);
      throw new ParametersException("Something wrong when read email template");
    }
    LOGGER.debug("Exit.");
  }

  /**
   * Get verify developer email content.
   *
   * @param developerId the developerId
   * @param verifyCode the verify code
   * @return email content
   */
  public String getVerifyContent(String developerId, String verifyCode) {
    LOGGER.debug("Enter. developer: {}.", developerId);

    Map<String, String> valuesMap = Maps.newHashMap();
    valuesMap.put("developerId", developerId);
    valuesMap.put("verifyCode", verifyCode);

    StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap);

    String content = strSubstitutor.replace(verifyTemplate);

    return content;
  }

  /**
   * Get reset password email content.
   *
   * @param developerId the developerId
   * @param resetToken the reset code
   * @return email content
   */
  public String getResetContent(String developerId, String resetToken) {
    LOGGER.debug("Enter. developer: {}.", developerId);

    Map<String, String> valuesMap = Maps.newHashMap();
    valuesMap.put("developerId", developerId);
    valuesMap.put("resetToken", resetToken);

    StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap);

    String content = strSubstitutor.replace(resetTemplate);

    return content;
  }
}

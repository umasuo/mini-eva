package cn.eva.mini.infra.util;

import cn.eva.mini.infra.exception.ConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator version.
 */
public final class VersionValidator {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(VersionValidator.class);

  /**
   * Instantiates a new Version validator.
   */
  private VersionValidator() {
  }

  /**
   * check the version.
   *
   * @param inputVersion Integer
   * @param existVersion Integer
   */
  public static void validate(Integer inputVersion, Integer existVersion) {
    if (!inputVersion.equals(existVersion)) {
      LOGGER.debug("DeviceDataDefinition version is not correct.");
      throw new ConflictException("DeviceDataDefinition version is not correct.");
    }
  }
}

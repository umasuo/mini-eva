package cn.eva.mini.infra.util;

import org.springframework.data.domain.AuditorAware;

/**
 * Zoned date time.
 */
public class ZonedDateTimeAuditorAware implements AuditorAware<String> {

  /**
   * getAllForApplicant current auditor.
   * @return null.
   */
  @Override
  public String getCurrentAuditor() {
    return null;
  }
}
package cn.eva.mini.application.dto.device;

import lombok.Data;

import java.io.Serializable;

/**
 * Device session. used in cache.
 */
@Data
public class DeviceSession implements Serializable {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = -6621681640958400777L;

  /**
   * Last update time.
   */
  private long lastUpdateTime;

  /**
   * Device view.
   */
  private DeviceView device;
}

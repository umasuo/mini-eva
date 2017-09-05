package cn.eva.mini.application.dto;

import lombok.Data;

import java.time.ZonedDateTime;

/**
 * Device.
 */
@Data
public class Device {

  /**
   * Device id.
   */
  private String id;

  /**
   * The Created at.
   */
  protected ZonedDateTime createdAt;

  /**
   * The Last modified at.
   */
  protected ZonedDateTime lastModifiedAt;

  /**
   * version used for update date check.
   */
  private Integer version;

  /**
   * device definition id.
   */
  private String deviceDefineId;

  /**
   * 开发者自定义的设备ID.
   */
  private String customizedId;

  /**
   * Device owner id.
   */
  private String ownerId;

  /**
   * Developer id of this device.
   */
  private String developerId;
}

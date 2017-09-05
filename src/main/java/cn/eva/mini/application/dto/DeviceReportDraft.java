package cn.eva.mini.application.dto;

import lombok.Data;

/**
 * Device report draft.
 */
@Data
public class DeviceReportDraft {

  /**
   * The developer id.
   */
  private String developerId;

  /**
   * The Device definition id.
   */
  private String deviceDefinitionId;

  /**
   * The increase number.
   */
  private int registerNumber;

  /**
   * 当前的活跃用户(活跃用户的定义为当天有请求的用户，每天的活跃用户则为当天23点的活跃用户数：不同时区不一样).
   */
  private int onlineNumber;

  /**
   * total number of this hour.
   */
  private int totalNumber;

}

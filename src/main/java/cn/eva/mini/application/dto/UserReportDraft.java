package cn.eva.mini.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * User report draft.
 */
@Data
public class UserReportDraft implements Serializable {

  private static final long serialVersionUID = 3014600617785980921L;
  /**
   * The developer id.
   */
  private String developerId;

  /**
   * Start time of this hour.
   */
  private Long startTime;

  /**
   * The increase number of the hour.
   */
  private int increaseNumber;

  /**
   * 当前的活跃用户(活跃用户的定义为当天有请求的用户，每天的活跃用户则为当天23点的活跃用户数：不同时区不一样).
   */
  private int activeNumber;

  /**
   * total user count at this time.
   */
  private int totalNumber;
}

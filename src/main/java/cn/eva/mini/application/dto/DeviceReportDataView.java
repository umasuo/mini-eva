package cn.eva.mini.application.dto;

import lombok.Data;

/**
 * Device report data view.
 */
@Data
public class DeviceReportDataView {
  /**
   * The Device definition id.
   */
  private String deviceDefinitionId;

  /**
   * The Local date.
   */
  private Long date;

  /**
   * The Register number.
   */
  private int increaseNumber;

  /**
   * The Online number.
   */
  private int activeNumber;

  /**
   * The Total number.
   */
  private int totalNumber;
}

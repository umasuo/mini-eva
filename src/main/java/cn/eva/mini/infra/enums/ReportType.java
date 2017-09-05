package cn.eva.mini.infra.enums;

/**
 * Report type, including daily, weekly, monthly, annual.
 */
public enum ReportType {
  /**
   * Daily report type.
   */
  DAILY("daily"),

  /**
   * Weekly report type.
   */
  WEEKLY("weekly"),

  /**
   * Monthly report type.
   */
  MONTHLY("monthly"),

  /**
   * Annual report type.
   */
  ANNUAL("annual");

  /**
   * The type.
   */
  private String type;

  /**
   * Private constructor.
   *
   * @param type the type
   */
  private ReportType(String type) {
    this.type = type;
  }

  /**
   * Gets type.
   *
   * @return type
   */
  public String getType() {
    return this.type;
  }
}

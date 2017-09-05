package cn.eva.mini.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 产品类型。
 */
@Data
public class ProductTypeView implements Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = 8675853413590870401L;

  /**
   * The id.
   */
  private String id;

  /**
   * The name.
   */
  private String name;

  /**
   * The groupName.
   */
  private String groupName;

  /**
   * The functions.
   */
  private List<CommonFunctionView> functions;

  /**
   * The data.
   */
  private List<CommonDataView> data;

  /**
   * The version.
   */
  private Integer version;
}

package cn.eva.mini.application.dto.product;

import cn.eva.mini.application.dto.data.DeviceDataView;
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
  private List<ProductFunctionView> functions;

  /**
   * The data.
   */
  private List<DeviceDataView> data;

  /**
   * The version.
   */
  private Integer version;
}

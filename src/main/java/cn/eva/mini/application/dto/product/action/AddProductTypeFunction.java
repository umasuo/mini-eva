package cn.eva.mini.application.dto.product.action;

import cn.eva.mini.application.dto.product.function.FunctionDataType;
import cn.eva.mini.infra.enums.TransferType;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加产品类别的功能的action。
 */
@Data
public class AddProductTypeFunction implements ProductTypeAction {

  /**
   * The functionId.
   */
  @NotNull
  private String functionId;

  /**
   * The name.
   */
  @NotNull
  private String name;

  /**
   * The description.
   */
  private String description;

  /**
   * The transferType.
   */
  @NotNull
  private TransferType transferType;

  /**
   * The dataType.
   */
  @NotNull
  private FunctionDataType dataType;

  /**
   * Get action name: addProductTypeFunction.
   *
   * @return addProductTypeFunction
   */
  @Override
  public String getActionName() {
    return ProductActionNames.ADD_PRODUCT_TYPE_FUNCTION;
  }
}

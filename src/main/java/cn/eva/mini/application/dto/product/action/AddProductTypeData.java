package cn.eva.mini.application.dto.product.action;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加产品类别的数据的action。
 */
@Data
public class AddProductTypeData implements ProductTypeAction {

  /**
   * 数据格点ID，需要对开发者唯一，例如: s001。
   */
  @NotNull
  private String dataId;

  /**
   * 数据定义的名称，例如"手环步数"
   */
  @NotNull
  private String name;

  /**
   * 数据定义介绍，主要用于介绍此数据格点的用途，目的等。
   */
  private String description;

  /**
   * 数据具体的结构.
   */
  @NotNull
  private String schema;

  /**
   * 数据定义id。
   */
  private String productTypeId;

  /**
   * Get action name: addProductTypeData.
   *
   * @return addProductTypeData
   */
  @Override
  public String getActionName() {
    return ProductActionNames.ADD_PRODUCT_TYPE_DATA;
  }
}

package cn.eva.mini.application.dto.product.action;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Product type update request.
 */
public class ProductTypeUpdateRequest {
  /**
   * The expected version of the category on which the changes should be applied.
   * If the expected version does not match the actual version, a 409 Conflict will be returned.
   */
  @NotNull
  @Min(0)
  private Integer version;

  /**
   * Array of UpdateAction.
   * The list of update action to be performed on the category.
   * Required.
   */
  @NotNull
  @Valid
  private List<ProductTypeAction> actions;

  /**
   * convert to UpdateActions.
   *
   * @return list of UpdateAction
   */
  public List<ProductTypeAction> getActions() {
    return actions.stream().map(action -> (ProductTypeAction) action).collect(Collectors.toList());
  }

  /**
   * get version.
   *
   * @return integer
   */
  public Integer getVersion() {
    return version;
  }

  /**
   * set version.
   *
   * @param version integer
   */
  public void setVersion(Integer version) {
    this.version = version;
  }

  /**
   * set actions.
   *
   * @param actions list of update action
   */
  public void setActions(List<ProductTypeAction> actions) {
    this.actions = actions;
  }
}

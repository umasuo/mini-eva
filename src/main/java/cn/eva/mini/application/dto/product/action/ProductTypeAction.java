package cn.eva.mini.application.dto.product.action;

import cn.eva.mini.infra.updater.UpdateAction;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * configurations for common update actions that will be used in more thant one service
 * and this action also extends other action configure in each service.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "action")
@JsonSubTypes({
    //update action for product.
    @JsonSubTypes.Type(value = AddProductTypeFunction.class, name = ProductActionNames.ADD_PRODUCT_TYPE_FUNCTION),
    @JsonSubTypes.Type(value = AddProductTypeData.class, name = ProductActionNames.ADD_PRODUCT_TYPE_DATA),
})
public interface ProductTypeAction extends Serializable , UpdateAction {

  /**
   * get action name.
   *
   * @return name in string.
   */
  String getActionName();
}

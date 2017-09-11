package cn.eva.mini.application.dto.product.mapper;

import cn.eva.mini.application.dto.product.ProductFunctionView;
import cn.eva.mini.domain.entity.ProductFunction;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Mapper class for ProductFunction.
 */
public final class ProductFunctionMapper {

  /**
   * Private constructor.
   */
  private ProductFunctionMapper() {
  }

  /**
   * To model list.
   *
   * @param entities the entities
   * @return the list
   */
  public static List<ProductFunctionView> toModel(List<ProductFunction> entities) {
    List<ProductFunctionView> models = Lists.newArrayList();

    if (entities != null) {
      entities.stream().forEach(
          entity -> models.add(toModel(entity))
      );
    }

    return models;
  }

  /**
   * Convert ProductFunction to ProductFunctionView.
   *
   * @param entity the entity
   * @return the product function view
   */
  public static ProductFunctionView toModel(ProductFunction entity) {
    ProductFunctionView model = new ProductFunctionView();

    model.setId(entity.getId());
    model.setFunctionId(entity.getFunctionId());
    model.setName(entity.getName());
    model.setDescription(entity.getDescription());
    model.setDataType(entity.getDataType());
    model.setTransferType(entity.getTransferType());

    return model;
  }
}

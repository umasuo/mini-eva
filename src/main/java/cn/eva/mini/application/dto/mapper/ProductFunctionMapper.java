package cn.eva.mini.application.dto.mapper;

import cn.eva.mini.application.dto.ProductFunctionView;
import cn.eva.mini.domain.entity.CommonFunction;
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

  /**
   * 拷贝一份新的CommonFunction列表。
   *
   * @param functions 原始的CommonFunction列表
   * @return 新拷贝的CommonFunction列表 list
   */
  public static List<ProductFunction> copy(List<CommonFunction> functions) {
    List<ProductFunction> productFunctions = Lists.newArrayList();

    functions.stream().forEach(function -> productFunctions.add(copy(function)));

    return productFunctions;
  }

  /**
   * 拷贝一份新的CommonFunction实体。
   *
   * @param function CommonFunction实体
   * @return 新拷贝的CommonFunction实体 product function
   */
  public static ProductFunction copy(CommonFunction function) {
    ProductFunction productFunction = new ProductFunction();

    productFunction.setFunctionId(function.getFunctionId());
    productFunction.setName(function.getName());
    productFunction.setDescription(function.getDescription());
    productFunction.setTransferType(function.getTransferType());
    productFunction.setDataType(function.getDataType());

    return productFunction;
  }
}

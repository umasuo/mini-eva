package cn.eva.mini.application.dto.product.mapper;

import cn.eva.mini.application.dto.data.DataDefinitionView;
import cn.eva.mini.application.dto.product.ProductTypeDraft;
import cn.eva.mini.application.dto.product.ProductTypeView;
import cn.eva.mini.domain.entity.ProductType;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 该类用于转换ProductType与ProductTypeView。
 */
public final class ProductTypeMapper {

  /**
   * Private constructor.
   */
  private ProductTypeMapper() {
  }

  /**
   * 把ProductType列表转换为ProductTypeView列表，并且加上对应的CommonDataView。
   *
   * @param entities        ProductType list
   * @param dataDefinitions CommonDataView list
   * @return list build ProductTypeView
   */
  public static List<ProductTypeView> toView(List<ProductType> entities, Map<String, List<DataDefinitionView>> dataDefinitions) {
    List<ProductTypeView> views = Lists.newArrayList();

    entities.stream().forEach(
      entity -> views.add(toView(entity, dataDefinitions))
    );

    return views;
  }

  /**
   * 把ProductType转换为ProductTypeView，并且加上对应的CommonDataView。
   *
   * @param entity           ProductType
   * @param productDataViews CommonDataView list
   * @return ProductTypeView product type view
   */
  public static ProductTypeView toView(ProductType entity, Map<String, List<DataDefinitionView>> productDataViews) {
    ProductTypeView view = toView(entity);

    List<DataDefinitionView> dataViews = Lists.newArrayList();
    if (!CollectionUtils.isEmpty(productDataViews) && productDataViews.containsKey(entity.getId())) {
      dataViews = productDataViews.get(entity.getId());
    }

    view.setData(dataViews);

    return view;
  }


  /**
   * Convert ProductType into ProductTypeView.
   *
   * @param entity the entity
   * @return the product type view
   */
  public static ProductTypeView toView(ProductType entity) {
    ProductTypeView view = new ProductTypeView();

    view.setId(entity.getId());
    view.setName(entity.getName());
    view.setGroupName(entity.getGroupName());
    view.setFunctions(ProductFunctionMapper.toModel(entity.getFunctions()));
    view.setData(Lists.newArrayList());
    view.setVersion(entity.getVersion());

    return view;
  }

  /**
   * Convert ProductType into ProductTypeView.
   *
   * @param entity the entity
   * @return the product type view
   */
  public static ProductTypeView toView(ProductType entity, List<DataDefinitionView> productDataViews) {
    ProductTypeView view = new ProductTypeView();

    view.setId(entity.getId());
    view.setName(entity.getName());
    view.setGroupName(entity.getGroupName());
    view.setFunctions(ProductFunctionMapper.toModel(entity.getFunctions()));
    view.setData(productDataViews);
    view.setVersion(entity.getVersion());

    return view;
  }


  /**
   * Convert ProductTypeDraft into ProductType.
   *
   * @param draft the draft
   * @return the product type
   */
  public static ProductType toModel(ProductTypeDraft draft) {
    ProductType productType = new ProductType();

    productType.setName(draft.getName());
    productType.setGroupName(draft.getGroupName());

    return productType;
  }
}

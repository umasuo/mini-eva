package cn.eva.mini.application.dto.mapper;


import cn.eva.mini.application.dto.ProductDraft;
import cn.eva.mini.application.dto.ProductView;
import cn.eva.mini.domain.entity.Product;
import cn.eva.mini.infra.enums.ProductStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Product mapper.
 */
public final class ProductMapper {

  private ProductMapper() {
  }

  /**
   * convert from view to domain model
   */
  public static Product toModel(ProductDraft draft, String developerId) {
    Product product = new Product();

    product.setDeveloperId(developerId);
    product.setIcon(draft.getIcon());
    product.setName(draft.getName());
    product.setProductType(draft.getProductTypeId());
    product.setCommunicationType(draft.getType());
    if (draft.getOpenable() != null) {
      product.setOpenable(draft.getOpenable());
    }

    product.setStatus(ProductStatus.DEVELOPING);

    return product;
  }

  /**
   * convert domain model to view.
   */
  public static ProductView toView(Product product) {
    ProductView view = new ProductView();
    view.setId(product.getId());
    view.setProductTypeId(product.getProductType());
    view.setCreatedAt(product.getCreatedAt());
    view.setLastModifiedAt(product.getLastModifiedAt());
    view.setVersion(product.getVersion());
    view.setDeveloperId(product.getDeveloperId());
    view.setIcon(product.getIcon());
    view.setStatus(product.getStatus());
    view.setName(product.getName());
    view.setType(product.getCommunicationType());
    view.setOpenable(product.getOpenable());

    view.setModel(product.getModel());
    view.setFirmwareVersion(product.getFirmwareVersion());
    view.setWifiModule(product.getWifiModule());
    view.setDescription(product.getDescription());

    view.setFunctions(ProductFunctionMapper.toModel(product.getProductFunctions()));

    return view;
  }

  /**
   * convert list build model to list build views.
   */
  public static List<ProductView> toView(List<Product> products) {
    List<ProductView> views = new ArrayList<>();
    if (products != null) {
      products.stream().forEach(
          product -> views.add(toView(product))
      );
    }
    return views;
  }
}

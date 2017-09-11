package cn.eva.mini.application.dto.data.mapper;

import cn.eva.mini.application.dto.data.DataDefinitionDraft;
import cn.eva.mini.application.dto.data.DataDefinitionView;
import cn.eva.mini.domain.entity.DeviceDataDefinition;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * DataDefinitionMapper.
 */
public final class DataDefinitionMapper {

  /**
   * private Default constructor.
   */
  private DataDefinitionMapper() {
  }

  /**
   * To model list.
   *
   * @param entities the entities
   * @return the list
   */
  public static List<DataDefinitionView> toView(List<DeviceDataDefinition> entities) {
    List<DataDefinitionView> models = Lists.newArrayList();

    Consumer<DeviceDataDefinition> consumer = dataDefinition -> models.add(toView(dataDefinition));

    entities.stream().forEach(consumer);

    return models;
  }

  /**
   * To model data definition view.
   *
   * @param model the model
   * @return the data definition view
   */
  public static DataDefinitionView toView(DeviceDataDefinition model) {
    DataDefinitionView view = null;
    if (model != null) {
      view = new DataDefinitionView();
      view.setId(model.getId());
      view.setCreatedAt(model.getCreatedAt());
      view.setLastModifiedAt(model.getLastModifiedAt());
      view.setVersion(model.getVersion());
      view.setDeveloperId(model.getDeveloperId());
      view.setDataId(model.getDataId());
      view.setName(model.getName());
      view.setDescription(model.getDescription());
      view.setOpenable(model.getOpenable());
      view.setSchema(model.getDataSchema());
    }
    return view;
  }

  /**
   * 根据draft 创建数据定义model.
   */
  public static DeviceDataDefinition toModel(DataDefinitionDraft draft, String developerId) {

    DeviceDataDefinition model = null;
    model = new DeviceDataDefinition();
    model.setDeveloperId(developerId);
    model.setProductId(draft.getProductId());
    model.setDataId(draft.getDataId());
    model.setName(draft.getName());
    model.setDescription(draft.getDescription());
    model.setDataSchema(draft.getDataSchema().toString());
    if (draft.getOpenable() != null) {
      model.setOpenable(draft.getOpenable());
    }

    return model;
  }


  /**
   * To model map.
   *
   * @param dataDefinitions
   * @return
   */
  public static Map<String, DeviceDataDefinition> toModelMap(
    List<DeviceDataDefinition> dataDefinitions) {
    Map<String, DeviceDataDefinition> entityMap =
      dataDefinitions.stream().collect(Collectors.toMap(x -> x.getId(), x -> x));

    return entityMap;
  }
}

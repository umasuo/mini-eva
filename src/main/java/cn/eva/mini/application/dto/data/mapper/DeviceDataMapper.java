package cn.eva.mini.application.dto.data.mapper;

import cn.eva.mini.application.dto.data.DeviceDataDraft;
import cn.eva.mini.application.dto.data.DeviceDataView;
import cn.eva.mini.domain.entity.DeviceData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Device data mapper.
 */
public final class DeviceDataMapper {

  /**
   * Mapper.
   */
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  /**
   * Default constructor.
   */
  private DeviceDataMapper() {
  }


  /**
   * View to model.
   *
   * @param view
   * @return
   */
  public static DeviceData toModel(DeviceDataView view) {
    DeviceData model = null;
    if (view != null) {
      model = new DeviceData();
      model.setId(view.getId());
      model.setCreatedAt(view.getCreatedAt());
      model.setLastModifiedAt(view.getLastModifiedAt());
      model.setDeviceId(view.getDeviceId());
      model.setDataDefinitionId(view.getDataDefinitionId());
      model.setVersion(view.getVersion());
//      model.setData(view.getData());

      model.setUserId(view.getUserId());
      model.setDeveloperId(view.getDeveloperId());
      model.setDeviceDefinitionId(view.getDeviceDefinitionId());
    }
    return model;
  }

  /**
   * view to model.
   *
   * @param dataDraft
   * @param developerId
   * @param userId
   * @return
   */
  public static DeviceData toModel(DeviceDataDraft dataDraft, String developerId, String
      userId) {
    DeviceData model = null;
    if (dataDraft != null) {
      model = new DeviceData();
      model.setDataDefinitionId(dataDraft.getDataId());
      model.setDeviceId(dataDraft.getDeviceId());
      model.setData(dataDraft.getData().toString());

      model.setUserId(userId);
      model.setDeveloperId(developerId);
      model.setDeviceDefinitionId(dataDraft.getDeviceDefinitionId());
    }
    return model;
  }

  /**
   * Model to view.
   *
   * @param model
   * @return
   */
  public static DeviceDataView toView(DeviceData model) {
    DeviceDataView view = null;
    if (model != null) {
      view = new DeviceDataView();
      view.setId(model.getId());
      view.setCreatedAt(model.getCreatedAt());
      view.setLastModifiedAt(model.getLastModifiedAt());
      view.setDeviceId(model.getDeviceId());
      view.setDataDefinitionId(model.getDataDefinitionId());
      view.setVersion(model.getVersion());
      try {
        view.setData(OBJECT_MAPPER.readTree(model.getData()));
      } catch (IOException ex) {
        view.setData(null);
      }

      view.setUserId(model.getUserId());
      view.setDeveloperId(model.getDeveloperId());
      view.setDeviceDefinitionId(model.getDeviceDefinitionId());
    }
    return view;
  }

  /**
   * To view list.
   *
   * @param dataList
   * @return
   */
  public static List<DeviceDataView> toView(List<DeviceData> dataList) {
    List<DeviceDataView> viewList = new ArrayList<>();
    dataList.stream().forEach(
        data -> viewList.add(toView(data))
    );
    return viewList;
  }
}

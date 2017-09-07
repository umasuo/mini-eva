package cn.eva.mini.application.dto.data;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Device data draft.
 */
@Data
public class DeviceDataDraft {

  /**
   * data definition id, used to check if the data is in the correct structure.
   */
  @NotNull
  private String dataId;

  /**
   * device id.
   */
  @NotNull
  private String deviceId;

  /**
   * the real structured json data.
   */
  @NotNull
  private JsonNode data;

  /**
   * 设备定义的ID，冗余数据，方便后期查询.
   */
  private String deviceDefinitionId;
}

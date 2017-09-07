package cn.eva.mini.application.dto.data;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Device Data View.
 */
@Data
public class DeviceDataView {

  /**
   * Device data id.
   */
  private String id;

  /**
   * Create time.
   */
  protected Long createdAt;

  /**
   * Update time.s
   */
  protected Long lastModifiedAt;

  /**
   * Data version.
   */
  private Integer version;

  /**
   * data definition id, used to check if the data is in the correct structure.
   */
  @NotNull
  private String dataDefinitionId;

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

  // the next three filed is redundancy for search or process.
  /**
   * User id.
   */
  @NotNull
  private String userId;

  /**
   * Developer id.
   */
  private String developerId;

  /**
   * Device definition id.
   */
  private String deviceDefinitionId;
}

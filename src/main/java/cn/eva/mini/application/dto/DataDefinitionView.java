package cn.eva.mini.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DataDefinitionView.
 */
@Data
public class DataDefinitionView implements Serializable {

  /**
   * auto generated serial id.
   */
  private static final long serialVersionUID = 7500245666736988395L;

  /**
   * auto generated uuid.
   */
  private String id;

  /**
   * The Created at.
   */
  protected Long createdAt;

  /**
   * The Last modified at.
   */
  protected Long lastModifiedAt;

  /**
   * version used for update date check.
   */
  private Integer version;

  /**
   * which developer this data definition belong to.
   */
  private String developerId;

  /**
   * data id defined by the developer.
   */
  private String dataId;

  /**
   * Data schema.
   */
  private String schema;

  /**
   * name of this definition.
   */
  private String name;

  /**
   * describe the usage of this definition.
   */
  private String description;

  /**
   * The Openable.
   * True means other developers can find this data, false means not.
   */
  private Boolean openable;

}

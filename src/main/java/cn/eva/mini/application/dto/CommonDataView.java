package cn.eva.mini.application.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.io.Serializable;

import javax.persistence.Transient;

/**
 * CommonDataView.
 */
@Data
public class CommonDataView implements Serializable{

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -8861282658167694700L;

  /**
   * The id.
   */
  private String id;

  /**
   * The dataId.
   */
  private String dataId;

  /**
   * The name.
   */
  private String name;

  /**
   * Version used for update date check.
   */
  private Integer version;

  /**
   * The data structure.
   */
  private String schema;

  /**
   * The dataSchema.
   */
  @Transient
  private transient JsonNode dataSchema;

  /**
   * The description.
   */
  private String description;
}

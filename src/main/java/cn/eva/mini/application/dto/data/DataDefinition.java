package cn.eva.mini.application.dto.data;

import cn.eva.mini.infra.util.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * Data definition.
 */
@Data
public class DataDefinition {

  /**
   * auto generated uuid.
   */
  private String id;

  /**
   * The Created at.
   */
  protected ZonedDateTime createdAt;

  /**
   * The Last modified at.
   */
  protected ZonedDateTime lastModifiedAt;

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
   * the data structure.
   */
  private String schema;

  /**
   * Redundancy of json schema.
   */
  private JsonNode jsonSchema;

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

  /**
   * Schema setter.
   *
   * @param schema
   */
  public void setSchema(String schema) {
    this.schema = schema;
    jsonSchema = JsonUtils.deserialize(schema, JsonNode.class);
  }

  /**
   * Getter.
   *
   * @return
   */
  public String getSchema() {
    return schema;
  }

  /**
   * Getter of json schema.
   *
   * @return
   */
  public JsonNode getJsonSchema() {
    return jsonSchema;
  }

  /**
   * Setter of json schema.
   *
   * @param jsonSchema
   */
  public void setJsonSchema(JsonNode jsonSchema) {
    this.jsonSchema = jsonSchema;
  }
}

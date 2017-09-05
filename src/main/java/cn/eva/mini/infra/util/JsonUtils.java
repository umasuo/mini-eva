package cn.eva.mini.infra.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * JsonUtils for serialize or deserialize mode.
 */
public final class JsonUtils {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

  /**
   * Object mapper.
   */
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  /**
   * Private default constructor.
   */
  private JsonUtils() {
  }

  /**
   * Deserializer.
   *
   * @param string
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T> T deserialize(String string, Class<T> clazz) {
    if (StringUtils.isBlank(string)) {
      throw new IllegalArgumentException(
          "Blank string cannot be deserialized to class");
    }

    try {
      return OBJECT_MAPPER.readValue(string, clazz);
    } catch (IOException e) {
      LOGGER.error("Error deserializing string: " + string, e);
      throw new IllegalArgumentException("Error deserializing", e);
    }
  }

  /**
   * Deserializer.
   *
   * @param string
   * @param typeReference
   * @param <T>
   * @return
   */
  public static <T> T deserialize(String string, TypeReference<T> typeReference) {
    if (StringUtils.isBlank(string)) {
      throw new IllegalArgumentException(
          "Blank string cannot be deserialized to class");
    }

    try {
      return OBJECT_MAPPER.readValue(string, typeReference);
    } catch (IOException e) {
      LOGGER.error("Error deserializing string: " + string, e);
      throw new IllegalArgumentException("Error deserializing", e);
    }
  }

  /**
   * Serializer.
   *
   * @param obj
   * @return
   */
  public static String serialize(Object obj) {
    if (obj == null) {
      throw new IllegalArgumentException("Null Object cannot be serialized");
    }

    try {
      return OBJECT_MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      LOGGER.error("Error serializing object", e);
      throw new IllegalArgumentException("Error serializing object: " + e.getMessage());
    }
  }

  /**
   * Check is json is valid.
   *
   * @param string
   * @return
   */
  public static boolean isJSONValid(String string) {
    try {
      OBJECT_MAPPER.readTree(string);
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Check is json is valid.
   *
   * @param string
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T> boolean isJSONValid(String string, Class<T> clazz) {
    if (StringUtils.isBlank(string)) {
      return false;
    }

    try {
      OBJECT_MAPPER.readValue(string, clazz);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}

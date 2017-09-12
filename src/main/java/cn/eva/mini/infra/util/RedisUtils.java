package cn.eva.mini.infra.util;

/**
 * RedisUtils.
 */
public final class RedisUtils {

  /**
   * Platform data definition key.
   */
  public static final String PLATFORM_DEFINITION_KEY = "data:definition:platform";

  /**
   * Developer definition cache key.
   */
  public static final String DEVELOPER_DEFINITION_FORMAT = "data:definition:developer:%s";

  /**
   * Device definition key.
   */
  public static final String DEVICE_DEFINITION_FORMAT = "data:definition:%s:%s";


  /**
   * 缓存ProductType使用的key。
   */
  public static final String PRODUCT_TYPE_KEY = "product:producttype";

  /**
   * 缓存Product使用的key。
   */
  public static final String PRODUCT_KEY_FORMAT = "product:%s";


  /**
   * Phone key.
   */
  public static final String PHONE_KEY_FORMAT = "user:%s";

  /**
   * Sms code key
   */
  public static final String PHONE_CODE_KEY_FORMAT = "user:%s:%s";

  /**
   * User key.
   * "user:developerId:userId"
   */
  public static final String USER_KEY_FORMAT = "user:%s:%s";

  /**
   * User session key.
   */
  public static final String USER_SESSION_KEY = "session";

}

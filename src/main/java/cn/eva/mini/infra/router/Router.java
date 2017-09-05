package cn.eva.mini.infra.router;

/**
 * API router.
 */
public class Router {
  /**
   * data center root.
   */
  public static final String DATA_CENTER_ROOT = "/v1/data";


  /**********************************Data definition**************************************/

  /**
   * data definition root.
   */
  public static final String DATA_DEFINITION_ROOT = "/data-definitions";

  /**
   * definition with id.
   */
  public static final String DATA_DEFINITION_WITH_ID = DATA_DEFINITION_ROOT + "/{id}";

  /**
   * Copy root.
   */
  public static final String DATA_COPY = DATA_DEFINITION_ROOT + "/copy";

  /**
   * Platform data definition root.
   */
  public static final String PLATFORM_DATA_ROOT = DATA_DEFINITION_ROOT + "/platform";

  /**
   * The constant PLATFORM_DATA_WITH_ID.
   */
  public static final String PLATFORM_DATA_WITH_ID = PLATFORM_DATA_ROOT + "/{id}";

  /**
   * Developer data definition root.
   */
  public static final String DEVELOPER_DATA_ROOT = DATA_DEFINITION_ROOT + "/developer";

  /**
   * Developer data definition with id.
   */
  public static final String DEVELOPER_DATA_WITH_ID = DEVELOPER_DATA_ROOT + "/{id}";


  /****************************************DEVICE CENTER***********************************************/

  /**
   * Version.
   */
  public static final String VERSION = "/v1";

  /**
   * device root.
   */
  public static final String DEVICE_CENTER_ROOT = VERSION + "/devices";

  /**
   * The constant REPORT_ROOT.
   */
  public static final String REPORT_ROOT = DEVICE_CENTER_ROOT + "/reports";

  /**
   * device with id.
   */
  public static final String DEVICE_CENTER_WITH_ID = DEVICE_CENTER_ROOT + "/{id}";

  /**
   * Device token root.
   */
  public static final String DEVICE_TOKEN = DEVICE_CENTER_ROOT + "/tokens";

  /**
   * Union root.
   */
  public static final String UNION_ROOT = DEVICE_CENTER_ROOT + "/unions";

  /**
   * Device message root.
   */
  public static final String DEVICE_MESSAGE = DEVICE_CENTER_WITH_ID + "/messages";

  /**
   * Device Data.
   */
  public static final String DEVICE_DATA = DEVICE_CENTER_ROOT + "/data";

  /**
   * Union register.
   */
  public static final String UNION_ROOT_REGISTER = UNION_ROOT + "/register";

  /**
   * Device count.
   */
  public static final String DEVICE_COUNT = "/v1/admin/devices/count";

}

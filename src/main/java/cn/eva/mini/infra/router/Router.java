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


  /******************FEEDBACK********************/
  /**
   * report root.
   */
  public static final String FEEDBACK_ROOT = "/v1/feedbacks";

  /**
   * The constant FEEDBACK_WITH_ID.
   */
  public static final String FEEDBACK_WITH_ID = FEEDBACK_ROOT + "/{id}";

  /*****************************PRODUCT*************************************/
  /**
   * The constant PRODUCT_ROOT.
   */
  public static final String PRODUCT_ROOT = VERSION + "/products";

  /**
   * The constant PRODUCT_WITH_ID.
   */
  public static final String PRODUCT_WITH_ID = PRODUCT_ROOT + "/{id}";

  /**
   * The constant PRODUCT_STATUS.
   */
  public static final String PRODUCT_STATUS = PRODUCT_WITH_ID + "/status";

  /**
   * The constant PRODUCT_TYPE_ROOT.
   */
  public static final String PRODUCT_TYPE_ROOT = PRODUCT_ROOT + "/types";

  /**
   * The constant PRODUCT_REQUEST.
   */
  public static final String PRODUCT_REQUEST = PRODUCT_ROOT + "/request";

  /**
   * The constant PRODUCT_REQUEST_WITH_ID.
   */
  public static final String PRODUCT_REQUEST_WITH_ID = PRODUCT_REQUEST + "/{}";

  /**
   * The constant ADMIN_PRODUCT_TYPE_ROOT.
   */
  public static final String ADMIN_PRODUCT_TYPE_ROOT = VERSION + "/admin/products/types";

  /**
   * The constant ADMIN_PRODUCT_TYPE_WITH_ID.
   */
  public static final String ADMIN_PRODUCT_TYPE_WITH_ID = ADMIN_PRODUCT_TYPE_ROOT + "/{id}";

  /**
   * The constant ADMIN_PRODUCT_COUNT.
   */
  public static final String ADMIN_PRODUCT_COUNT = ADMIN_PRODUCT_TYPE_ROOT + "/count";


  /******************************************USER*************************************************/
   /**
   * user root.
   */
  public static final String USER_ROOT = VERSION + "/users";

  /**
   * user root.
   */
  public static final String USER_WITH_ID = VERSION + "/users/{id}";

  /**
   * login.
   */
  public static final String USER_SIGN_IN = USER_ROOT + "/signin";

  /**
   * login with phone and password.
   */
  public static final String USER_SIGN_IN_PWD = USER_ROOT + "/login";

  /**
   * register.
   */
  public static final String USER_REGISTER = USER_ROOT + "/register";

  /**
   * register.
   */
  public static final String USER_RESET_PASSWORD = USER_ROOT + "/resetPassword";

  /**
   * login.
   */
  public static final String USER_SIGN_IN_STATUS = USER_ROOT + "/{id}/status";

  /**
   * logout.
   */
  public static final String USER_SIGN_OUT = USER_ROOT + "/{id}/signout";

  /**
   * sign up.
   */
  public static final String USER_SIGN_UP = USER_ROOT + "/signup";

  /**
   * The constant VALIDATION_CODE.
   */
  public static final String VALIDATION_CODE = USER_ROOT + "/validationCodes";

  /**
   * The constant PHONE_NUMBER.
   */
  public static final String PHONE_NUMBER = "phoneNumber";

  /**
   * The constant GROUP.
   */
  public static final String GROUP = USER_ROOT + "/groups";

  /**
   * The constant GROUP_ID.
   */
  public static final String GROUP_ID = "id";

  /**
   * The constant GROUP_WITH_ID.
   */
  public static final String GROUP_WITH_ID = GROUP + "/{" + GROUP_ID + "}";

  /**
   * The constant developer_id.
   */
  public static final String DEVELOPER_ID = "developerId";

  /**
   * The constant REPORT_ROOT.
   */
  public static final String USER_REPORT_ROOT = USER_ROOT + "/reports";

  /**
   * User count api.
   */
  public static final String USER_COUNT = "/v1/admin/users/count";
}

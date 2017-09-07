package cn.eva.mini.infra.router;

/**
 * API router.
 */
public class Router {

  public static final String API_VERSION = "/api/v1";

  public static final String ID = "id";

  /*********************************DATA***********************************************/
  /**
   * data center root.
   */
  public static final String DATA_ROOT = API_VERSION + "/data";

  /**
   * data with id.
   */
  public static final String DATA_WITH_ID = DATA_ROOT + "/{" + ID + "}";

  /**
   * data definition root.
   */
  public static final String DATA_DEFINITION_ROOT = DATA_ROOT + "/definitions";

  /**
   * definition with id.
   */
  public static final String DATA_DEFINITION_WITH_ID = DATA_DEFINITION_ROOT + "/{" + ID + "}";

  /****************************************DEVICE CENTER***********************************************/

  /**
   * device root.
   */
  public static final String DEVICE_ROOT = API_VERSION + "/devices";

  /**
   * device with id.
   */
  public static final String DEVICE_WITH_ID = DEVICE_ROOT + "/{" + ID + "}";

  /**
   * Device token root.
   */
  public static final String DEVICE_TOKEN = DEVICE_ROOT + "/tokens";

  /**
   * Union root.
   */
  public static final String UNION_ROOT = DEVICE_ROOT + "/unions";

  /**
   * Device count.
   */
  public static final String DEVICE_COUNT = DEVICE_ROOT + "/count";


  /******************FEEDBACK********************/
  /**
   * report root.
   */
  public static final String FEEDBACK_ROOT = API_VERSION + "/feedback";

  /**
   * The constant FEEDBACK_WITH_ID.
   */
  public static final String FEEDBACK_WITH_ID = FEEDBACK_ROOT + "/{" + ID + "}";

  /*****************************PRODUCT*************************************/
  /**
   * The constant PRODUCT_ROOT.
   */
  public static final String PRODUCT_ROOT = API_VERSION + "/products";

  /**
   * The constant PRODUCT_WITH_ID.
   */
  public static final String PRODUCT_WITH_ID = PRODUCT_ROOT + "/{" + ID + "}";

  /**
   * The constant PRODUCT_STATUS.
   */
  public static final String PRODUCT_STATUS = PRODUCT_WITH_ID + "/status";

  /**
   * The constant PRODUCT_TYPE_ROOT.
   */
  public static final String PRODUCT_TYPE_ROOT = PRODUCT_ROOT + "/types";

  /**
   * The constant ADMIN_PRODUCT_TYPE_ROOT.
   */
  public static final String ADMIN_PRODUCT_TYPE_ROOT = PRODUCT_ROOT + "/types";

  /**
   * The constant ADMIN_PRODUCT_TYPE_WITH_ID.
   */
  public static final String ADMIN_PRODUCT_TYPE_WITH_ID = ADMIN_PRODUCT_TYPE_ROOT + "/{" + ID + "}";

  /**
   * The constant ADMIN_PRODUCT_COUNT.
   */
  public static final String ADMIN_PRODUCT_COUNT = ADMIN_PRODUCT_TYPE_ROOT + "/count";


  /******************************************USER*************************************************/
  /**
   * user root.
   */
  public static final String USER_ROOT = API_VERSION + "/users";

  /**
   * user root.
   */
  public static final String USER_WITH_ID = USER_ROOT + "/{" + ID + "}";

  /**
   * login.
   */
  public static final String USER_QUICK_LOGIN = USER_ROOT + "/quickLgin";

  /**
   * login with phone and password.
   */
  public static final String USER_LOGIN = USER_ROOT + "/login";

  /**
   * register.
   */
  public static final String USER_REGISTER = USER_ROOT + "/register";

  /**
   * register.
   */
  public static final String USER_RESET_PASSWORD = USER_ROOT + "/resetPassword";

  /**
   * login status.
   */
  public static final String USER_STATUS = USER_ROOT + "/{" + ID + "}/status";

  /**
   * logout.
   */
  public static final String USER_LOGOUT = USER_ROOT + "/{" + ID + "}/logout";

  /**
   * The constant SMS_CODE.
   */
  public static final String SMS_CODE = USER_ROOT + "/smsCode";

  /**
   * The constant PHONE_NUMBER.
   */
  public static final String PHONE_NUMBER = "phoneNumber";

  /**
   * The constant GROUP.
   */
  public static final String GROUP = USER_ROOT + "/groups";

  /**
   * The constant GROUP_WITH_ID.
   */
  public static final String GROUP_WITH_ID = GROUP + "/{" + ID + "}";

  /**
   * User count api.
   */
  public static final String USER_COUNT = USER_ROOT + "/count";


  /***************************************DEVELOPER******************************************/
  /**
   * developer root.
   */
  public static final String DEVELOPER_ROOT = API_VERSION + "/developers";

  /**
   * login.
   */
  public static final String DEVELOPER_LOGIN = DEVELOPER_ROOT + "/login";

  /**
   * sign up.
   */
  public static final String DEVELOPER_REGISTER = DEVELOPER_ROOT + "/register";

  /**
   * Developer with id.
   */
  public static final String DEVELOPER_WITH_ID = DEVELOPER_ROOT + "/{" + ID + "}";

  /**
   * login.
   */
  public static final String DEVELOPER_STATUS = DEVELOPER_WITH_ID + "/status";

  /**
   * logout.
   */
  public static final String DEVELOPER_LOGOUT = DEVELOPER_WITH_ID + "/logout";

  /**
   * verify developer after sign up.
   */
  public static final String DEVELOPER_VERIFY = DEVELOPER_WITH_ID + "/verify";

  /**
   * Reset developer's password.
   */
  public static final String DEVELOPER_RESET_PASSWORD = DEVELOPER_ROOT + "/reset-password";

  /**
   * Get all Developers.
   */
  public static final String DEVELOPER_GET_ALL = DEVELOPER_ROOT + "/all";

  /**
   * Count developers.
   */
  public static final String DEVELOPER_COUNT = DEVELOPER_ROOT + "/count";
}

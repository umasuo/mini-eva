package cn.eva.mini.infra.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * exception body.
 * return customized code and message to the client.
 */
@Getter
@Setter
public class ExceptionBody {

  public static final int DEVELOPER_NOT_EXIST_CODE = 20001;
  public static final String DEVELOPER_NOT_EXIST_MESSAGE = "developer not exist.";

  /**
   * User not exist code.
   */
  public static final int USER_NOT_EXIST_CODE = 10001;
  /**
   * User not exist message.
   */
  public static final String USER_NOT_EXIST_MESSAGE = "user not exist.";

  /**
   * User already exist code.
   */
  public static final int USER_ALREADY_EXIST_CODE = 10002;
  /**
   * User already exist message.
   */
  public static final String USER_ALREADY_EXIST_MESSAGE = "user already exist.";

  /**
   * Password not correct code.
   */
  public static final int EMAIL_OR_PASSWORD_ERROR_CODE = 10003;

  /**
   * Password not correct message.
   */
  public static final String EMAIL_OR_PASSWORD_ERROR_MESSAGE = "password not correct.";
  /**
   * CODE.
   */
  private int code;

  /**
   * Message
   */
  private String message;

  public static ExceptionBody of(int code, String message) {
    ExceptionBody body = new ExceptionBody();
    body.code = code;
    body.message = message;
    return body;
  }
}

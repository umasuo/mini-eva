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

  public static final int DEVELOPER_NOT_EXIST_CODE = 10001;
  public static final String DEVELOPER_NOT_EXIST_MESSAGE = "developer not exist.";

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

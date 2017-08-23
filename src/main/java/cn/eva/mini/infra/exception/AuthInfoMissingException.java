package cn.eva.mini.infra.exception;

public class AuthInfoMissingException extends RuntimeException {

  /**
   * default constructor.
   */
  public AuthInfoMissingException() {
    super();
  }

  /**
   * constructor with message.
   *
   * @param msg
   */
  public AuthInfoMissingException(String msg) {
    super(msg);
  }
}

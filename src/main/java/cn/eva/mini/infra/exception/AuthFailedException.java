package cn.eva.mini.infra.exception;

public class AuthFailedException extends RuntimeException {

  /**
   * default constructor.
   */
  public AuthFailedException() {
    super();
  }

  /**
   * constructor with msg.
   *
   * @param msg message
   */
  public AuthFailedException(String msg) {
    super(msg);
  }
}

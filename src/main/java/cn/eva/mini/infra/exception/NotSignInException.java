package cn.eva.mini.infra.exception;

/**
 * Not sign in exception.
 */
public class NotSignInException extends RuntimeException {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = -9188653671581494470L;

  /**
   * Constructor.
   */
  public NotSignInException() {
    super();
  }

  /**
   * Constructor.
   *
   * @param msg
   */
  public NotSignInException(String msg) {
    super(msg);
  }

  /**
   * Constructor.
   *
   * @param msg
   * @param e
   */
  public NotSignInException(String msg, Throwable e) {
    super(msg, e);
  }
}

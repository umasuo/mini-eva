package cn.eva.mini.infra.exception;

/**
 * password error exception.
 */
public class PasswordErrorException extends RuntimeException{
  /**
   * default constructor.
   */
  public PasswordErrorException() {
    super();
  }

  /**
   * constructor with message.
   *
   * @param message String
   */
  public PasswordErrorException(String message) {
    super(message);
  }
}

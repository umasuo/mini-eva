package cn.eva.mini.infra.exception;

public class ConflictException extends RuntimeException {

  /**
   * Instantiates a new Conflict exception.
   */
  public ConflictException() {
    super();
  }

  /**
   * Instantiates a new Conflict exception.
   *
   * @param message the message
   */
  public ConflictException(String message) {
    super(message);
  }
}

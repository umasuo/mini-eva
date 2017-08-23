package cn.eva.mini.infra.exception;

public class ImmutableException extends RuntimeException {

  /**
   * Instantiates a new Immutable exception.
   */
  public ImmutableException() {
    super();
  }

  /**
   * Instantiates a new Immutable exception.
   *
   * @param message the message
   */
  public ImmutableException(String message) {
    super(message);
  }
}

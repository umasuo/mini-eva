package cn.eva.mini.infra.auth;

public enum TokenType {

  /**
   * used for anonymous request.
   */
  ANONYMOUS("anonymous"),

  /**
   * used for normal customer's request.
   */
  CUSTOMER("customer"),

  /**
   * used for admin of the system.
   */
  DEVELOPER("developer");

  /**
   * value value.
   */
  private String value;

  TokenType(String value) {
    this.value = value;
  }

  /**
   * @return String value
   */
  public String getValue() {
    return this.value;
  }

  /**
   * get type by value.
   *
   * @param value String value
   * @return TokenType
   */
  public static TokenType getType(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Token type value should not be null.");
    }
    TokenType result = ANONYMOUS;
    switch (value) {
      case "anonymous":
        result = ANONYMOUS;
        break;
      case "customer":
        result = CUSTOMER;
        break;
      case "developer":
        result = DEVELOPER;
        break;
    }
    return result;
  }
}

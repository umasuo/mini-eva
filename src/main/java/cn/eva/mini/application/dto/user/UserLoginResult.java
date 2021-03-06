package cn.eva.mini.application.dto.user;

import cn.eva.mini.application.dto.user.UserView;

import java.io.Serializable;

/**
 * The type Sign in result.
 */
public class UserLoginResult implements Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = 517596150259943683L;

  /**
   * Customer for view.
   */
  private UserView userView;

  /**
   * String token.
   */
  private String token;

  /**
   * Instantiates a new Sign in result.
   *
   * @param userView the user view
   * @param token    the token
   */
  public UserLoginResult(UserView userView, String token) {
    this.userView = userView;
    this.token = token;
  }

  /**
   * Getter.
   *
   * @return
   */
  public UserView getUserView() {
    return userView;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setUserView(UserView userView) {
    this.userView = userView;
  }

  /**
   * Getter.
   *
   * @return
   */
  public String getToken() {
    return token;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setToken(String token) {
    this.token = token;
  }
}

package cn.eva.mini.application.dto.user.mapper;


import cn.eva.mini.application.dto.user.UserQuickLogin;
import cn.eva.mini.domain.entity.User;

/**
 * Sign in mapper.
 */
public final class UserSignInMapper {

  /**
   * Instantiates a new Sign in mapper.
   */
  private UserSignInMapper() {
  }

  /**
   * To platform user platform user.
   *
   * @param signIn the sign in
   * @return the platform user
   */
  public static User toModel(UserQuickLogin signIn) {
    User user = null;
    if (signIn != null) {
      user = new User();
      user.setPhone(signIn.getPhone());
      //TODO external id, for now, we do not support.
    }
    return user;
  }

}

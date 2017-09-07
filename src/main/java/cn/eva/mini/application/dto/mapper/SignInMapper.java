package cn.eva.mini.application.dto.mapper;


import cn.eva.mini.application.dto.user.UserQuickSignIn;
import cn.eva.mini.domain.entity.User;

/**
 * Sign in mapper.
 */
public final class SignInMapper {

  /**
   * Instantiates a new Sign in mapper.
   */
  private SignInMapper() {
  }

  /**
   * To platform user platform user.
   *
   * @param signIn the sign in
   * @return the platform user
   */
  public static User toModel(UserQuickSignIn signIn) {
    User user = null;
    if (signIn != null) {
      user = new User();
      user.setPhone(signIn.getPhone());
      //TODO external id, for now, we do not support.
    }
    return user;
  }

}

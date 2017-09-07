package cn.eva.mini.application.dto.mapper;

import cn.eva.mini.application.dto.user.UserRegisterInfo;
import cn.eva.mini.application.dto.user.UserView;
import cn.eva.mini.domain.entity.User;
import cn.eva.mini.infra.util.PasswordUtil;

/**
 * User view mapper.
 */
public final class UserMapper {

  /**
   * Private default constructor.
   */
  private UserMapper() {
  }

  /**
   * Get user view from platform and developer.
   *
   * @param user user
   * @return UserView user view
   */
  public static UserView toView(User user) {
    UserView view = null;
    if (user != null) {
      view = new UserView();
      view.setUserId(user.getId());
      view.setDeveloperId(user.getDeveloperId());
      view.setDeviceDefinitionId(user.getDeviceDefinitionId());
      view.setEmail(user.getEmail());
      view.setPhone(user.getPhone());
      view.setExternalId(user.getExternalId());
      if (user.getUserDetail() != null) {
        view.setAge(user.getUserDetail().getAge());
        view.setIcon(user.getUserDetail().getIcon());
        view.setName(user.getUserDetail().getName());
        view.setCountry(user.getUserDetail().getCountry());
        view.setSignature(user.getUserDetail().getSignature());
      }
    }
    return view;
  }

  /**
   * Create user from register info.
   *
   * @param register
   * @return
   */
  public static User toModel(UserRegisterInfo register) {
    User user = new User();
    user.setDeveloperId(register.getDeveloperId());
    user.setPhone(register.getPhone());
    user.setPassword(PasswordUtil.hashPassword(register.getPassword()));

    return user;
  }
}

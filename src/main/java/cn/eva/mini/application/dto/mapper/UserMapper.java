package cn.eva.mini.application.dto.mapper;

import cn.eva.mini.application.dto.UserView;
import cn.eva.mini.domain.entity.User;

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
}

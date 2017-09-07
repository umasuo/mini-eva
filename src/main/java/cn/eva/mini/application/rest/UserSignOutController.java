package cn.eva.mini.application.rest;

import cn.eva.mini.application.service.SignOutApplication;
import cn.eva.mini.infra.router.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * User sign out service.
 */
@RestController
public class UserSignOutController {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(UserSignOutController.class);

  /**
   * sign up service
   */
  @Autowired
  private transient SignOutApplication signOutService;

  /**
   * sign out.
   *
   * @param userId
   * @param developerId
   */
  @DeleteMapping(value = Router.USER_LOGOUT)
  public void quickSignIn(@PathVariable String userId, @RequestHeader String developerId) {
    LOGGER.info("Enter. userId: {}, developerId: {}.", userId, developerId);

    signOutService.signOut(userId, developerId);

    LOGGER.info("Exit.");
  }

}

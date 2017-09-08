package cn.eva.mini.application.rest;

import cn.eva.mini.application.dto.user.UserLogin;
import cn.eva.mini.application.dto.user.UserLoginResult;
import cn.eva.mini.application.dto.user.UserQuickLogin;
import cn.eva.mini.application.dto.user.UserRegisterInfo;
import cn.eva.mini.application.service.RegisterApplication;
import cn.eva.mini.application.service.SignInApplication;
import cn.eva.mini.application.service.SignOutApplication;
import cn.eva.mini.infra.router.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Sign in controller.
 */
@RestController
public class UserController {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  /**
   * sign up service
   */
  @Autowired
  private transient SignInApplication signInService;

  /**
   * sign up service
   */
  @Autowired
  private transient SignOutApplication signOutService;

  /**
   * sign up service
   */
  @Autowired
  private transient RegisterApplication registerApplication;

  /**
   * User quick login.
   * If the user not exist, then create a new user.
   *
   * @param login quick login model
   * @return sign in result
   */
  @PostMapping(value = Router.USER_QUICK_LOGIN)
  public UserLoginResult quickLogin(@RequestBody @Valid UserQuickLogin login) {
    LOGGER.info("Enter. login: {}", login);

    UserLoginResult loginResult = signInService.quickLogin(login);

    LOGGER.info("Exit. loginResult: {}", loginResult);
    return loginResult;
  }


  /**
   * user login with phone and password.
   *
   * @param signIn the sign in
   * @return sign in result
   */
  @PostMapping(value = Router.USER_LOGIN)
  public UserLoginResult login(@RequestBody @Valid UserLogin signIn) {
    LOGGER.info("Enter. quickLogin: {}", signIn);

    UserLoginResult loginResult = signInService.login(signIn);

    LOGGER.info("Exit. loginResult: {}", loginResult);
    return loginResult;
  }

  /**
   * user log out.
   *
   * @param userId
   * @param developerId
   */
  @DeleteMapping(value = Router.USER_LOGOUT)
  public void logout(@PathVariable String userId, @RequestHeader String developerId) {
    LOGGER.info("Enter. userId: {}, developerId: {}.", userId, developerId);

    signOutService.signOut(userId, developerId);

    LOGGER.info("Exit.");
  }

  /**
   * user sign up and sign in.
   *
   * @param register register info
   * @return sign in result
   */
  @PostMapping(value = Router.USER_REGISTER)
  public UserLoginResult register(@RequestBody @Valid UserRegisterInfo register) {
    LOGGER.info("Enter. register: {}", register);

    UserLoginResult signInResult = registerApplication.register(register);

    LOGGER.info("Exit. signInResult: {}", signInResult);
    return signInResult;
  }

}

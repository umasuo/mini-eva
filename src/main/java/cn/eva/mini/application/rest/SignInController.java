package cn.eva.mini.application.rest;

import cn.eva.mini.application.dto.user.UserQuickSignIn;
import cn.eva.mini.application.dto.user.UserSignInResult;
import cn.eva.mini.application.dto.user.UserSignIn;
import cn.eva.mini.application.service.SignInApplication;
import cn.eva.mini.infra.router.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Sign in controller.
 */
@RestController
public class SignInController {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignInController.class);

  /**
   * sign up service
   */
  @Autowired
  private transient SignInApplication signInService;

  /**
   * user sign up.
   *
   * @param signIn the sign in
   * @return sign in result
   */
  @PostMapping(value = Router.USER_SIGN_IN)
  public UserSignInResult quickSignIn(@RequestBody @Valid UserQuickSignIn signIn) {
    LOGGER.info("Enter. quickSignIn: {}", signIn);

    UserSignInResult signInResult = signInService.quickSignIn(signIn);

    LOGGER.info("Exit. signInResult: {}", signInResult);
    return signInResult;
  }


  /**
   * user sign in with phone and password.
   *
   * @param signIn the sign in
   * @return sign in result
   */
  @PostMapping(value = Router.USER_SIGN_IN_PWD)
  public UserSignInResult signin(@RequestBody @Valid UserSignIn signIn) {
    LOGGER.info("Enter. quickSignIn: {}", signIn);

    UserSignInResult signInResult = signInService.signIn(signIn);

    LOGGER.info("Exit. signInResult: {}", signInResult);
    return signInResult;
  }
}

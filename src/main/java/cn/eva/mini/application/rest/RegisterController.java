package cn.eva.mini.application.rest;

import cn.eva.mini.application.dto.user.UserSignInResult;
import cn.eva.mini.application.dto.user.UserRegisterInfo;
import cn.eva.mini.application.service.RegisterApplication;
import cn.eva.mini.infra.router.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Register controller.
 */
@RestController
public class RegisterController {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

  /**
   * sign up service
   */
  @Autowired
  private transient RegisterApplication registerApplication;

  /**
   * user sign up and sign in.
   *
   * @param register register info
   * @return sign in result
   */
  @PostMapping(value = Router.USER_REGISTER)
  public UserSignInResult register(@RequestBody @Valid UserRegisterInfo register) {
    LOGGER.info("Enter. register: {}", register);

    UserSignInResult signInResult = registerApplication.register(register);

    LOGGER.info("Exit. signInResult: {}", signInResult);
    return signInResult;
  }
}

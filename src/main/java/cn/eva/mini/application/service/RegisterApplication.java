package cn.eva.mini.application.service;

import cn.eva.mini.application.dto.user.UserSignInResult;
import cn.eva.mini.application.dto.user.UserRegisterInfo;
import cn.eva.mini.application.dto.mapper.UserMapper;
import cn.eva.mini.domain.entity.User;
import cn.eva.mini.domain.service.UserService;
import cn.eva.mini.infra.exception.AlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type register service.
 */
@Service
public class RegisterApplication {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(RegisterApplication.class);

  /**
   * platform user service.
   */
  @Autowired
  private transient UserService userService;


  /**
   * Sign in service.
   */
  @Autowired
  private transient SignInApplication signInApplication;

  /**
   * Validation service.
   */
  @Autowired
  private transient SmsApplication smsApplication;

  /**
   * register.
   * TODO 事务性!
   *
   * @param register the sign in
   * @return the sign in result
   */
  public UserSignInResult register(UserRegisterInfo register) {
    LOGGER.debug("Enter. register: {}", register);


    smsApplication.validateCode(register.getPhone(), register.getSmsCode());

    User user = userService.getUserByPhone(register.getPhone());
    if (user != null) {
      throw new AlreadyExistException("User already exist.");
    }

    user = userService.createUser(UserMapper.toModel(register));

    UserSignInResult result = signInApplication.signIn(user);

    LOGGER.debug("Exit. UserSignInResult: {}.", result);
    return result;
  }

}
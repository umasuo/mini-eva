package cn.eva.mini.application.service;

import cn.eva.mini.application.dto.QuickSignIn;
import cn.eva.mini.application.dto.SignInResult;
import cn.eva.mini.application.dto.UserSession;
import cn.eva.mini.application.dto.UserSignIn;
import cn.eva.mini.application.dto.UserView;
import cn.eva.mini.application.dto.mapper.SignInMapper;
import cn.eva.mini.application.dto.mapper.UserMapper;
import cn.eva.mini.domain.entity.User;
import cn.eva.mini.domain.service.UserService;
import cn.eva.mini.infra.exception.NotExistException;
import cn.eva.mini.infra.exception.PasswordErrorException;
import cn.eva.mini.infra.util.PasswordUtil;
import cn.eva.mini.infra.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * The type Sign in service.
 */
@Service
public class SignInApplication {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignInApplication.class);

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * platform user service.
   */
  @Autowired
  private transient UserService userService;

  /**
   * Message application.
   */
//  @Autowired
//  private MessageApplication messageApplication;

  /**
   * Validation service.
   */
  @Autowired
  private SmsApplication smsApplication;

  /**
   * sign in.
   * TODO 事务性!
   *
   * @param signIn the sign in
   * @return the sign in result
   */
  public SignInResult quickSignIn(QuickSignIn signIn) {
    LOGGER.debug("Enter. signIn: {}", signIn);

    SignInResult result;

    smsApplication.validateCode(signIn.getPhone(), signIn.getValidationCode());

    User user = userService.getUserByPhone(signIn.getPhone());

    if (user == null) {
      user = createUser(signIn);
    }

    result = signIn(user);

    LOGGER.debug("Exit. SignInResult: {}.", result);
    return result;
  }

  /**
   * 手机密码登录.
   *
   * @param signIn
   * @return
   */
  public SignInResult signIn(UserSignIn signIn) {
    LOGGER.debug("Enter. signIn: {}.", signIn);

    User user = userService.getUserByPhone(signIn.getPhone());
    if (user == null) {
      throw new NotExistException("User not exit for phone: " + signIn.getPhone());
    }

    if (!PasswordUtil.checkPassword(signIn.getPassword(), user.getPassword())) {
      throw new PasswordErrorException("phone number or password not correct!");
    }

    SignInResult result = signIn(user);

    LOGGER.debug("Exit. result: {}.", result);
    return result;
  }

  /**
   * 登录接口.
   *
   * @param user the user entity.
   * @return SignInResult
   */
  public SignInResult signIn(User user) {
    LOGGER.debug("Enter. User: {}.", user);

    UserView userView = UserMapper.toView(user);

    String token = UUID.randomUUID().toString();

    SignInResult signInResult = new SignInResult(userView, token);

    cacheSignInStatus(userView, token);

    LOGGER.debug("Exit. signInResult: {}.", signInResult);
    return signInResult;
  }


  /**
   * Create a new PlatformUser entity.
   *
   * @param signIn the signUpDeveloperUser info.
   * @return PlatformUser
   */
  private User createUser(QuickSignIn signIn) {
    LOGGER.debug("Enter. signUpDeveloperUser: {}.", signIn);

    User user = SignInMapper.toModel(signIn);
    User savedUser = userService.createUser(user);

    LOGGER.debug("Exit. User created: {}.", savedUser);
    return savedUser;
  }

  /**
   * cache the user's sign in status and info.
   */
  private void cacheSignInStatus(UserView userView, String token) {
    LOGGER.debug("Enter. userView: {}, token: {}.", userView, token);

    String userKey = String.format(RedisUtils.USER_KEY_FORMAT, userView.getDeveloperId(), userView.getUserId());

    UserSession session = new UserSession(userView, token);

    //cache the result
    redisTemplate.boundHashOps(userKey).put(RedisUtils.USER_SESSION_KEY, session);
    redisTemplate.expire(userKey, 30, TimeUnit.DAYS);//7天后过期
//    messageApplication.addMqttUser(userView.getUserId(), token);
    LOGGER.debug("Exit.");
  }

}

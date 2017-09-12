package cn.eva.mini.application.service;

import cn.eva.mini.application.dto.user.UserLoginResult;
import cn.eva.mini.application.dto.user.UserQuickLogin;
import cn.eva.mini.application.dto.user.UserRegisterInfo;
import cn.eva.mini.application.dto.user.UserSession;
import cn.eva.mini.application.dto.user.UserLogin;
import cn.eva.mini.application.dto.user.UserView;
import cn.eva.mini.application.dto.user.mapper.UserMapper;
import cn.eva.mini.domain.entity.User;
import cn.eva.mini.domain.service.UserService;
import cn.eva.mini.infra.exception.AlreadyExistException;
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
public class UserApplication {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(UserApplication.class);

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
  public UserLoginResult quickLogin(UserQuickLogin signIn) {
    LOGGER.debug("Enter. login: {}", signIn);

    UserLoginResult result;

    smsApplication.validateCode(signIn.getPhone(), signIn.getSmsCode());

    User user = userService.getByPhone(signIn.getPhone());

    if (user == null) {
      user = userService.create(UserMapper.toModel(signIn));
    }

    result = login(user);

    LOGGER.debug("Exit. UserLoginResult: {}.", result);
    return result;
  }

  /**
   * 手机密码登录.
   *
   * @param signIn
   * @return
   */
  public UserLoginResult login(UserLogin signIn) {
    LOGGER.debug("Enter. login: {}.", signIn);

    User user = userService.getByPhone(signIn.getPhone());
    if (user == null) {
      throw new NotExistException("User not exit for phone: " + signIn.getPhone());
    }

    if (!PasswordUtil.checkPassword(signIn.getPassword(), user.getPassword())) {
      throw new PasswordErrorException("phone number or password not correct!");
    }

    UserLoginResult result = login(user);

    LOGGER.debug("Exit. result: {}.", result);
    return result;
  }

  /**
   * 登录接口.
   *
   * @param user the user entity.
   * @return UserLoginResult
   */
  public UserLoginResult login(User user) {
    LOGGER.debug("Enter. User: {}.", user);

    UserView userView = UserMapper.toView(user);

    String token = UUID.randomUUID().toString();

    UserLoginResult loginResult = new UserLoginResult(userView, token);

    cacheLoginStatus(userView, token);

    LOGGER.debug("Exit. loginResult: {}.", loginResult);
    return loginResult;
  }

  /**
   * User logout application.
   * Clear cache, delete mqtt user.
   * @param userId
   * @param developerId
   */
  public void logout(String userId, String developerId) {
    LOGGER.debug("Enter. userId: {}, developerId: {}.", userId, developerId);

    String userKey = String.format(RedisUtils.USER_KEY_FORMAT, developerId, userId);
    redisTemplate.delete(userKey);

//    messageApplication.deleteMqttUser(userId);

    LOGGER.debug("Exit.");
  }

  /**
   * register.
   * TODO 事务性!
   *
   * @param register the sign in
   * @return the sign in result
   */
  public UserLoginResult register(UserRegisterInfo register) {
    LOGGER.debug("Enter. register: {}", register);

    smsApplication.validateCode(register.getPhone(), register.getSmsCode());

    User user = userService.getByPhone(register.getPhone());
    if (user != null) {
      throw new AlreadyExistException("User already exist.");
    }

    user = userService.create(UserMapper.toModel(register));

    UserLoginResult result = login(user);

    LOGGER.debug("Exit. UserLoginResult: {}.", result);
    return result;
  }

  /**
   * cache the user's sign in status and info.
   */
  private void cacheLoginStatus(UserView userView, String token) {
    LOGGER.debug("Enter. userView: {}, token: {}.", userView, token);

    String userKey = String.format(RedisUtils.USER_KEY_FORMAT, userView.getDeveloperId(), userView.getUserId());

    UserSession session = new UserSession(userView, token);

    //cache the result
    redisTemplate.boundHashOps(userKey).put(RedisUtils.USER_SESSION_KEY, session);
    redisTemplate.expire(userKey, UserSession.EXPIRE_IN, TimeUnit.DAYS);
    //todo    messageApplication.addMqttUser(userView.getUserId(), token);
    LOGGER.debug("Exit.");
  }

}

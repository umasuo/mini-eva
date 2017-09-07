package cn.eva.mini.domain.service;

import cn.eva.mini.domain.entity.User;
import cn.eva.mini.infra.exception.AlreadyExistException;
import cn.eva.mini.infra.exception.NotExistException;
import cn.eva.mini.infra.repository.UserRepository;
import cn.eva.mini.infra.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Developer user service.
 */
@Service
public class UserService {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  /**
   * user info repository.
   */
  @Autowired
  private transient UserRepository repository;

  /**
   * create user info.
   *
   * @param user the user
   * @return the developer user
   */
  public User createUser(User user) {
    LOGGER.debug("Enter. userInfo: {}", user);

    Example<User> example = Example.of(user);
    User userInDb = repository.findOne(example);
    if (userInDb != null) {
      throw new AlreadyExistException("The user already exit.");
    }

    if (user.getPassword() != null) {
      String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
      user.setPassword(hashedPassword);
    }
    userInDb = repository.save(user);
    //TODO maybe we should set an numerical id?
    LOGGER.debug("Exit. userInDb: {}", userInDb);
    return userInDb;
  }

  /**
   * Gets user by id.
   *
   * @param userId the user id
   * @return the user by id
   */
  public User getUserById(String userId) {
    LOGGER.debug("Enter. userId: {}.", userId);
    Assert.notNull(userId, "User id can not be null");

    User user = repository.findOne(userId);
    if (user == null) {
      LOGGER.debug("Can not find user: {}.", userId);
      throw new NotExistException("User not exist");
    }

    LOGGER.debug("Exit.");
    return user;
  }

  /**
   * Gets user by id.
   *
   * @param phone the user id
   * @return the user by id
   */
  public User getUserByPhone(String phone) {
    LOGGER.debug("Enter. phone: {}.", phone);
    Assert.notNull(phone, "Phone can not be null");

    User user = repository.findOneByPhone(phone);
    if (user == null) {
      LOGGER.debug("Can not find user: {}.", phone);
      throw new NotExistException("User not exist");
    }

    LOGGER.debug("Exit.");
    return user;
  }
  /**
   * Save user.
   *
   * @param user
   * @return
   */
  public User save(User user) {
    LOGGER.debug("Enter.");

    User dUser = repository.save(user);

    LOGGER.debug("Exit.");
    return dUser;
  }
}

package cn.eva.mini.application.service;

import cn.eva.mini.application.dto.developer.DeveloperLogin;
import cn.eva.mini.application.dto.developer.DeveloperLoginResult;
import cn.eva.mini.application.dto.developer.DeveloperRegister;
import cn.eva.mini.application.dto.developer.DeveloperSession;
import cn.eva.mini.application.dto.developer.DeveloperView;
import cn.eva.mini.application.dto.developer.mapper.DeveloperMapper;
import cn.eva.mini.domain.entity.Developer;
import cn.eva.mini.domain.service.DeveloperService;
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

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * Developer application.
 */
@Service
public class DeveloperApplication {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DeveloperApplication.class);

  /**
   * Redis client.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * Developer Service.
   */
  @Autowired
  private transient DeveloperService developerService;

  /**
   * Developer Register with email and password.
   *
   * @param register
   * @return
   */
  public DeveloperView register(DeveloperRegister register) {
    LOGGER.debug("Enter. register: {}.", register);

    Developer developer = developerService.getWithEmail(register.getEmail());
    if (developer != null) {
      throw new AlreadyExistException("Developer already exist.");
    }
    developer = developerService.create(register.getEmail(), register.getPassword());

    LOGGER.debug("Exit. new developer: {}.", developer);
    return DeveloperMapper.toView(developer);
  }

  /**
   * Developer login with email and password.
   *
   * @param login
   * @return
   */
  public DeveloperLoginResult login(DeveloperLogin login) {
    LOGGER.debug("Enter. login: {}.", login);
    Developer developer = developerService.getWithEmail(login.getEmail());
    if (developer == null) {
      throw new NotExistException("Developer not exist.");
    }

    if (!PasswordUtil.checkPassword(login.getPassword(), developer.getPassword())) {
      throw new PasswordErrorException("Account or password error.");
    }

    DeveloperView developerView = DeveloperMapper.toView(developer);
    String token = UUID.randomUUID().toString();
    cacheSession(developerView, token);

    DeveloperLoginResult result = new DeveloperLoginResult(developerView, token);

    LOGGER.debug("Exit. developer login result: {}.", result);
    return result;
  }

  /**
   * Sign out. When sign out, clear the cache.
   *
   * @param id developer id.
   */
  public void logout(String id) {
    LOGGER.debug("Enter. id: {}.", id);
    assert id != null;

    String key = String.format(RedisUtils.DEVELOPER_KEY_FORMAT, id);
    DeveloperSession session = (DeveloperSession) redisTemplate.opsForHash().get(key, RedisUtils.DEVELOPER_SESSION_KEY);
    redisTemplate.delete(key);

    LOGGER.debug("Exit.");
  }

  /**
   * Get all developers.
   *
   * @return
   */
  public List<DeveloperView> getAllDevelopers() {
    LOGGER.debug("Enter.");

    List<Developer> developers = developerService.getAllDevelopers();

    List<DeveloperView> result = DeveloperMapper.toView(developers);

    LOGGER.debug("Exit. developer size: {}.", result.size());

    return result;
  }

  /**
   * Cache developer login session.
   *
   * @param view
   * @param token
   */
  private void cacheSession(DeveloperView view, String token) {
    LOGGER.debug("Enter. view: {}, token: {}.", view, token);

    DeveloperSession session = new DeveloperSession(view, token);
    String developerKey = String.format(RedisUtils.DEVELOPER_KEY_FORMAT, view.getId());

    //cache the result
    redisTemplate.boundHashOps(developerKey).put(RedisUtils.DEVELOPER_SESSION_KEY, session);
    redisTemplate.expire(developerKey, DeveloperSession.EXPIRE_IN, TimeUnit.HOURS);
    LOGGER.debug("Exit.");
  }
}

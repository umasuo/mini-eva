package cn.eva.mini.domain.service;

import cn.eva.mini.domain.entity.Developer;
import cn.eva.mini.infra.enums.AccountStatus;
import cn.eva.mini.infra.exception.AlreadyExistException;
import cn.eva.mini.infra.exception.ConflictException;
import cn.eva.mini.infra.exception.NotExistException;
import cn.eva.mini.infra.repository.DeveloperRepository;
import cn.eva.mini.infra.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Developer service.
 */
@Service
public class DeveloperService {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(DeveloperService.class);

  /**
   * repository.
   * for this, we should consider to change our database.
   */
  @Autowired
  private transient DeveloperRepository repository;

  /**
   * create a developer from an sample.
   *
   * @param developer the sample
   * @return result. developer
   */
  public Developer save(Developer developer) {
    LOGGER.debug("CreateDeveloper: {}", developer);
    return this.repository.save(developer);
  }

  /**
   * create a developer with email & password.
   *
   * @param email    email
   * @param password raw password.
   * @return created developer
   */
  public Developer create(String email, String password) {
    LOGGER.debug("CreateDeveloper: email:{}", email);
    Developer developer = this.repository.findOneByEmail(email);
    if (developer != null) {
      throw new AlreadyExistException("Developer already existing.");
    }
    developer = new Developer();
    developer.setEmail(email);
    developer.setStatus(AccountStatus.UNVERIFIED);
    developer.setOpenable(false);//默认不公开任何数据

    String encryptedPwd = PasswordUtil.hashPassword(password);
    developer.setPassword(encryptedPwd);

    return save(developer);
  }

  /**
   * getAllForApplicant one developer by it's id.
   *
   * @param id developer id.
   * @return existing developer.
   */
  public Developer get(String id) {
    LOGGER.debug("GetDeveloper: id: {}", id);
    return this.repository.findOne(id);
  }

  /**
   * getAllForApplicant one developer with it's email.
   *
   * @param email the email
   * @return with email
   */
  public Developer getWithEmail(String email) {
    LOGGER.debug("GetDeveloper: email: {}", email);
    Developer developer = this.repository.findOneByEmail(email);
    if (developer == null) {
      throw new NotExistException("Developer not exist.");
    }
    return developer;
  }

  /**
   * Count developers.
   *
   * @return
   */
  public Long countDevelopers() {
    LOGGER.debug("Enter.");

    long count = repository.count();

    LOGGER.debug("Exit. developer count: {}.", count);

    return count;
  }

  /**
   * Get all developers.
   *
   * @return
   */
  public List<Developer> getAllDevelopers() {
    LOGGER.debug("Enter.");

    List<Developer> developers = repository.findAll();

    LOGGER.debug("Exit. developer size: {}.", developers.size());

    return developers;
  }

  /**
   * check the version.
   *
   * @param inputVersion Integer
   * @param existVersion Integer
   */
  private void checkVersion(Integer inputVersion, Integer existVersion) {
    if (!inputVersion.equals(existVersion)) {
      LOGGER.debug("Developer version is not correct.");
      throw new ConflictException("Developer version is not correct.");
    }
  }
}

package cn.eva.mini.application.service;

import cn.eva.mini.application.dto.developer.DeveloperView;
import cn.eva.mini.application.dto.developer.mapper.DeveloperMapper;
import cn.eva.mini.domain.entity.Developer;
import cn.eva.mini.domain.service.DeveloperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


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
}

package cn.eva.mini.application.rest;

import cn.eva.mini.application.dto.DeveloperView;
import cn.eva.mini.application.service.DeveloperApplication;
import cn.eva.mini.domain.entity.Developer;
import cn.eva.mini.domain.service.DeveloperService;
import cn.eva.mini.infra.router.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Developer controller.
 */
@CrossOrigin
@RestController
public class DeveloperController {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DeveloperController.class);

  /**
   * The DeveloperService.
   */
  @Autowired
  private transient DeveloperService developerService;

  /**
   * Developer application.
   */
  @Autowired
  private transient DeveloperApplication developerApplication;

  /**
   * Check if developer exist.
   * 内部接口，API Gateway不配置外部访问的路劲。
   *
   * @param id the developer id
   * @return true if developer exist and false if not
   */
  @GetMapping(Router.DEVELOPER_WITH_ID)
  public boolean checkDeveloper(@PathVariable(Router.ID) String id) {
    LOGGER.info("Enter. developerId: {}.", id);

    boolean result = true;
    Developer developer = developerService.get(id);
    if (developer == null) {
      LOGGER.debug("Developer: {} not exist.", id);
      result = false;
    }

    LOGGER.info("Exit. developer: {} exist? {}.", id, result);

    return result;
  }

  /**
   * Get all developers.
   *
   * @return
   */
  @GetMapping(Router.DEVELOPER_GET_ALL)
  public List<DeveloperView> getAllDevelopers() {
    LOGGER.info("Enter.");

    List<DeveloperView> developers = developerApplication.getAllDevelopers();

    LOGGER.debug("Exit. developer size: {}.", developers.size());

    return developers;
  }
}

package cn.eva.mini.application.rest;

import cn.eva.mini.application.dto.developer.DeveloperView;
import cn.eva.mini.application.service.DeveloperApplication;
import cn.eva.mini.domain.service.DeveloperService;
import cn.eva.mini.infra.router.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
   * Get all developers.
   * Used by admin site.
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

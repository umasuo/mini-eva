package cn.eva.mini.application.rest;

import cn.eva.mini.application.dto.user.GroupDraft;
import cn.eva.mini.application.dto.user.GroupView;
import cn.eva.mini.application.service.GroupApplication;
import cn.eva.mini.infra.router.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;


/**
 * Rest controller for group.
 */
@RestController
@CrossOrigin
public class GroupController {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

  /**
   * The Group application.
   */
  @Autowired
  private transient GroupApplication groupApplication;

  /**
   * Create group.
   *
   * @param groupDraft the group draft
   * @return the group
   */
  @PostMapping(Router.GROUP)
  public GroupView create(@RequestBody @Valid GroupDraft groupDraft,
                          @RequestHeader String userId,
                          @RequestHeader String developerId) {
    LOGGER.info("Enter. groupDraft: {}, userId: {}, developerId: {}.", groupDraft, userId,
        developerId);

    GroupView result = groupApplication.create(groupDraft, userId, developerId);

    LOGGER.info("Exit. groupView: {}.", result);
    return result;
  }

  /**
   * Delete.
   *
   * @param groupId the group id
   * @param version the version
   */
  @DeleteMapping(Router.GROUP_WITH_ID)
  public void delete(@PathVariable(Router.GROUP_ID) String groupId,
                     @RequestParam("version") Integer version,
                     @RequestHeader String developerId) {
    LOGGER.info("Enter. groupId: {}, version: {}, developerId: {}.", groupId, version, developerId);

    //todo developer id 没用，而且这个接口是给user用的，userID没有
    groupApplication.delete(groupId, version);

    LOGGER.info("Exit");
  }


  /**
   * Find one group.
   *
   * @param groupId the group id
   * @return the group view
   */
  @GetMapping(Router.GROUP_WITH_ID)
  public GroupView findOne(@PathVariable(Router.GROUP_ID) String groupId) {
    LOGGER.info("Enter. groupId: {}.", groupId);

    GroupView result = groupApplication.findOne(groupId);

    LOGGER.info("Exit.");

    return result;
  }

  /**
   * Find all group.
   * todo user? developer? device?
   *
   * @return the list
   */
  @GetMapping(Router.GROUP)
  public List<GroupView> findAll(@RequestParam(Router.DEVELOPER_ID) String developerId) {
    LOGGER.info("Enter. developerId: {}.", developerId);

    List<GroupView> result = groupApplication.findAll(developerId);

    LOGGER.info("Exit.");

    return result;
  }
}

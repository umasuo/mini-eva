package cn.eva.mini.application.service;

import cn.eva.mini.application.dto.user.GroupDraft;
import cn.eva.mini.application.dto.user.GroupView;
import cn.eva.mini.application.dto.mapper.GroupMapper;
import cn.eva.mini.domain.entity.Group;
import cn.eva.mini.domain.service.GroupService;
import cn.eva.mini.infra.exception.ConflictException;
import cn.eva.mini.infra.exception.NotExistException;
import cn.eva.mini.infra.util.VersionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Group application.
 */
@Service
public class GroupApplication {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(GroupApplication.class);

  /**
   * The Group service.
   */
  @Autowired
  private GroupService groupService;

  /**
   * Create group.
   *
   * @param groupDraft the group draft
   * @return the group
   */
  @Transactional
  public GroupView create(GroupDraft groupDraft, String userId, String developerId) {
    LOGGER.debug("Enter. groupDraft: {}.", groupDraft);

    Group createdGroup = GroupMapper.toModel(groupDraft, userId, developerId);
    Group savedGroup = groupService.save(createdGroup);

    //改parent的子类
    String parentId = groupDraft.getParentId();
    if (parentId != null) {
      Group parentGroup = groupService.findParentGroup(parentId);
      if (parentGroup == null) {
        throw new NotExistException("Parent group not exist.");
      }
      parentGroup.getChildrenId().add(savedGroup.getId());
      groupService.save(parentGroup);
    }

    GroupView result = GroupMapper.toView(savedGroup);

    LOGGER.debug("Exit. groupView: {}.", result);
    return result;
  }

  /**
   * Delete.
   *
   * @param groupId the group id
   * @param version the version
   */
  public void delete(String groupId, Integer version) {
    LOGGER.debug("Enter. groupId: {}, version: {}.", groupId, version);

    Group group = groupService.findOne(groupId);

    VersionValidator.validate(group.getVersion(), version);

    if (group.getUsers() != null &&
      !group.getUsers().isEmpty()) {
      LOGGER.debug("Can not delete group when there is {} users.",
        group.getUsers().size());
      throw new ConflictException("Users is not null");
    }

    if (group.getChildrenId() != null && !group.getChildrenId().isEmpty()) {
      LOGGER.debug("Can not delete group when there is {} sub groups.",
        group.getChildrenId().size());
      throw new ConflictException("Sub groups is not null");
    }

    groupService.delete(groupId);

    LOGGER.debug("Exit");
  }

  /**
   * Find one group.
   *
   * @param groupId the group id
   * @return the group view
   */
  public GroupView findOne(String groupId) {
    LOGGER.debug("Enter. groupId: {}.", groupId);

    Group group = groupService.findOne(groupId);

    GroupView result = GroupMapper.toView(group);

    LOGGER.debug("Exit. groupView: {}.", result);

    return result;
  }

  /**
   * Find all group by developer id.
   *
   * @param developerId the developer id
   * @return the list
   */
  public List<GroupView> findAll(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    List<Group> groups = groupService.findAllGroup(developerId);

    List<GroupView> result = GroupMapper.toView(groups);

    LOGGER.debug("Exit.");

    return result;
  }
}

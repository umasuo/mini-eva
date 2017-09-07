package cn.eva.mini.application.dto.mapper;

import cn.eva.mini.application.dto.user.GroupDraft;
import cn.eva.mini.application.dto.user.GroupView;
import cn.eva.mini.domain.entity.Group;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Consumer;

/**
 * Mapper for group.
 */
public final class GroupMapper {

  /**
   * Private default constructor.
   */
  private GroupMapper() {
  }

  /**
   * Convert to Group entity.
   *
   * @param draft the draft
   * @return the group
   */
  public static Group toModel(GroupDraft draft, String userId, String developerId) {
    Group entity = new Group();

    entity.setParent(draft.getParentId());
    entity.setName(draft.getName());
    entity.setOwnerId(userId);
    entity.setDeveloperId(developerId);

    return entity;
  }

  /**
   * Convert to GroupView.
   *
   * @param entity the entity
   * @return the group view
   */
  public static GroupView toView(Group entity) {

    GroupView model = new GroupView();

    model.setId(entity.getId());
    model.setDeveloperId(entity.getDeveloperId());
    model.setName(entity.getName());
    model.setOwnerId(entity.getOwnerId());
    model.setManagers(entity.getManagers());
    model.setUsers(entity.getUsers());
    model.setChildrenId(entity.getChildrenId());
    model.setParentId(entity.getParent());

    return model;
  }

  /**
   * To model list.
   *
   * @param entities the entities
   * @return the list
   */
  public static List<GroupView> toView(List<Group> entities) {

    List<GroupView> result = Lists.newArrayList();
    Consumer<Group> consumer = group -> result.add(toView(group));
    entities.stream().forEach(consumer);

    return result;
  }
}

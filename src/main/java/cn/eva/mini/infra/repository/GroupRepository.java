package cn.eva.mini.infra.repository;

import cn.eva.mini.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for group entity.
 *
 */
public interface GroupRepository extends JpaRepository<Group, String> {

  /**
   * Find by developer id.
   *
   * @param developerId the developer id
   * @return the list
   */
  List<Group> findByDeveloperId(String developerId);

  /**
   * Find by children id.
   *
   * @param childrenId the children id
   * @return the group
   */
  Group findByChildrenId(String childrenId);

  /**
   * Find by parent id.
   *
   * @param parentId the parent id
   * @return the list
   */
  List<Group> findByParent(String parentId);
}

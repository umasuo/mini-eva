package cn.eva.mini.infra.repository;

import cn.eva.mini.domain.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Developer repository.
 */
@Repository
public interface DeveloperRepository extends JpaRepository<Developer, String> {

  /**
   * Find one by email.
   *
   * @param email the email
   * @return the developer
   */
  Developer findOneByEmail(String email);

  /**
   * Find by openable.
   *
   * @param openable the openable
   * @return the list
   */
  List<Developer> findByOpenable(Boolean openable);
}

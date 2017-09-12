package cn.eva.mini.infra.repository;

import cn.eva.mini.domain.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

}

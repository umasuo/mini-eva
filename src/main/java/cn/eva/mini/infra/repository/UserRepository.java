package cn.eva.mini.infra.repository;

import cn.eva.mini.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

  /**
   * Find user by email.
   *
   * @param email
   * @return
   */
  User findOneByEmail(String email);

  /**
   * Find user by phone.
   *
   * @param phone
   * @return
   */
  User findOneByPhone(String phone);
}

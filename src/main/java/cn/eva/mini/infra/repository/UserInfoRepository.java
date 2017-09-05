package cn.eva.mini.infra.repository;

import cn.eva.mini.domain.entity.UserDetailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User info repository.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserDetailInfo, String> {

}

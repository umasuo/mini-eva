package cn.eva.mini.infra.repository;

import cn.eva.mini.domain.entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DeviceDataRepository.
 */
@Repository
public interface DeviceDataRepository extends JpaRepository<DeviceData, String> {

  /**
   * Query.
   * @param developerId
   * @param userId
   * @param dataId
   * @param deviceId
   * @param start
   * @param end
   * @return
   */
  @Query("select d from DeviceData d where d.developerId = ?1 and  d.userId = ?2 and d.dataDefinitionId = ?3 and d.deviceId = ?4 and d.createdAt > ?5 and d.createdAt < ?6")
  List<DeviceData> query(String developerId,
                         String userId,
                         String dataId,
                         String deviceId,
                         Long start,
                         Long end);

  /**
   *  Query.
   * @param developerId
   * @param userId
   * @param dataDefinitionId
   * @param deviceId
   * @return
   */
  @Query("select d from DeviceData d where d.developerId = ?1 and  d.userId = ?2 and d.dataDefinitionId = ?3 and d.deviceId = ?4")
  List<DeviceData> query(String developerId,
                         String userId,
                         String dataDefinitionId,
                         String deviceId);

}

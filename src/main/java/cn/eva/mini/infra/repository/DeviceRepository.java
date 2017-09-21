package cn.eva.mini.infra.repository;

import cn.eva.mini.domain.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Device repository.
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

  /**
   * Get report.
   *
   * @return
   */
  @Query("select new map(d.developerId as developerId, d.productTypeId as productTypeId, count(d) as totalCount) from Device d group by d.developerId, d.productTypeId")
  List<HashMap> getReport();

  /**
   * Get report with developer id.
   *
   * @param developerId
   * @return
   */
  @Query("select new map(d.developerId as developerId, d.productTypeId as productTypeId, count(d) as totalCount) from Device d group by d.developerId, d.productTypeId having d.developerId = ?1")
  List<HashMap> getReport(String developerId);

  /**
   * Get report data by time.
   *
   * @param startTime
   * @param endTime
   * @return
   */
  @Query("select new map(d.developerId as developerId, d.productTypeId as productTypeId, count(d) as registerCount) from Device d where d.createdAt >= ?1 and d.createdAt < ?2 group by d.developerId, d" +
    ".productTypeId")
  List<HashMap> getIncreaseReport(long startTime, long endTime);

  /**
   * Get developer register report data.
   *
   * @param developerId
   * @param startTime
   * @return
   */
  @Query("select new map(d.developerId as developerId, d.productTypeId as productTypeId, count(d) as registerCount) from Device d where d.createdAt >= ?2 group by d.developerId, d.productTypeId having d" +
    ".developerId = ?1")
  List<HashMap> getDeveloperRegisterReport(String developerId, long startTime);
}

package cn.eva.mini.infra.repository;

import cn.eva.mini.domain.entity.DeviceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Device report repository.
 */
public interface DeviceReportRepository extends JpaRepository<DeviceReport, String> {

  /**
   * Gets report by date.
   *
   * @param developerId the developer id
   * @param startDate   the start date
   * @param endDate     the end date
   * @return the report by date
   */
  @Query("select r from DeviceReport r where r.developerId = ?1 and r.startTime >= ?2 and r" +
      ".startTime <= ?3 order by r.startTime")
  List<DeviceReport> getReportByDate(String developerId, long startDate, long endDate);
}

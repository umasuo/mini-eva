package cn.eva.mini.domain.service;

import cn.eva.mini.domain.entity.DeviceReport;
import cn.eva.mini.infra.repository.DeviceReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for device report.
 */
@Service
public class DeviceReportService {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DeviceReportService.class);

  /**
   * The Device report repository.
   */
  @Autowired
  private transient DeviceReportRepository repository;

  /**
   * Gets report by date.
   *
   * @param developerId the developer id
   * @param startDate the start date
   * @param endDate the end date
   * @return the report by date
   */
  public List<DeviceReport> getReportByDate(String developerId, long startDate, long endDate) {
    LOGGER.debug("Enter. developerId: {}, startDate: {}, endDate: {}.",
        developerId, startDate, endDate);

    List<DeviceReport> reports = repository.getReportByDate(developerId, startDate, endDate);

    LOGGER.debug("Exit. report size: {}.", reports.size());
    return reports;
  }

  /**
   * Save all.
   *
   * @param reports the reports
   */
  public void saveAll(List<DeviceReport> reports) {
    LOGGER.debug("Enter. reports size: {}.", reports.size());

    repository.save(reports);

    LOGGER.debug("Exit.");
  }
}

package cn.eva.mini.infra.util;

import cn.eva.mini.application.dto.report.DeviceReportView;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Reports utils.
 */
public final class DeviceReportUtils {

  /**
   * Instantiates a new Report utils.
   */
  private DeviceReportUtils() {
  }

  /**
   * Merge report.
   *
   * @param totalReports    the total reports
   * @param registerReports the register reports
   * @return the list
   */
  public static List<DeviceReportView> mergeReport(List<HashMap> totalReports,
                                                   List<HashMap> registerReports) {
    List<DeviceReportView> result = Lists.newArrayList();

    Consumer<HashMap> totalConsumer = map -> handleTotalReport(result, map);

    totalReports.forEach(totalConsumer);

    Consumer<HashMap> registerConsumer = map -> handleRegisterReport(result, map);

    registerReports.forEach(registerConsumer);

    return result;
  }

  /**
   * Handle register report.
   *
   * @param result
   * @param map
   */
  private static void handleRegisterReport(List<DeviceReportView> result, Map map) {
//    Consumer<DeviceReportView> consumer = deviceReportView -> {
//      if (deviceReportView.getDeveloperId().equals(map.get("developerId").toString()) &&
//        deviceReportView.getDeviceDefinitionId().equals(map.get("productTypeId").toString())) {
//        deviceReportView.setRegisterNumber(Integer.valueOf(map.get("registerCount").toString()));
//      }
//    };
//
//    result.stream().forEach(consumer);
  }

  /**
   * Handle total report.s
   *
   * @param result
   * @param map
   */
  private static void handleTotalReport(List<DeviceReportView> result, Map map) {
    DeviceReportView reportView = new DeviceReportView();

//    reportView.setDeveloperId(map.get("developerId").toString());
//    reportView.setDeviceDefinitionId(map.get("productTypeId").toString());
//    reportView.setTotalNumber((Long) map.get("totalCount"));
//
//    // TODO: 17/6/16
//    reportView.setOnlineNumber(0);

    result.add(reportView);
  }
}

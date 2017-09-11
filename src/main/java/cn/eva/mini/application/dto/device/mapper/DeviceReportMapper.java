package cn.eva.mini.application.dto.device.mapper;

import cn.eva.mini.application.dto.report.DeviceReportDraft;
import cn.eva.mini.application.dto.report.DeviceReportView;
import cn.eva.mini.domain.entity.DeviceReport;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Device report mapper.
 */
public final class DeviceReportMapper {

  /**
   * Instantiates a new Device report mapper.
   */
  private DeviceReportMapper() {
  }

  /**
   * To model list.
   *
   * @param entities the entities
   * @return the list
   */
  public static List<DeviceReportView> toModel(List<DeviceReport> entities) {
    List<DeviceReportView> models = Lists.newArrayList();

    Consumer<DeviceReport> consumer = deviceReport -> models.add(toModel(deviceReport));

    entities.stream().forEach(consumer);

    return models;
  }

  private static DeviceReportView toModel(DeviceReport entity) {
    DeviceReportView model = new DeviceReportView();

    model.setDeviceDefinitionId(entity.getDeviceDefinitionId());
    //TODO finish this data

    return model;
  }

  /**
   * To entity list.
   *
   * @param reportDrafts the report drafts
   * @param startTime    the yesterday date
   * @return the list
   */
  public static List<DeviceReport> toEntity(List<DeviceReportDraft> reportDrafts, Long startTime) {
    List<DeviceReport> entities = Lists.newArrayList();

    reportDrafts.stream().forEach(
        reportDraft -> {
          entities.add(toEntity(reportDraft, startTime));
        }
    );

    return entities;
  }

  /**
   * To entity device report.
   *
   * @param draft     the draft
   * @param startTime the yesterday date
   * @return the device report
   */
  public static DeviceReport toEntity(DeviceReportDraft draft, Long startTime) {
    DeviceReport entity = new DeviceReport();

    entity.setDeveloperId(draft.getDeveloperId());
    entity.setDeviceDefinitionId(draft.getDeviceDefinitionId());
    entity.setStartTime(startTime);
    entity.setIncreaseNumber(draft.getRegisterNumber());
    entity.setActiveNumber(draft.getOnlineNumber());
    entity.setTotalNumber(draft.getTotalNumber());

    return entity;
  }


  /**
   * 将以小时为单位统计的数据转换为以天为单位统计的数据,这一步可以在数据库中完成.
   *
   * @param hourlyReport
   * @return
   */
  public static List<DeviceReportView> hourlyToDaily(List<DeviceReport> hourlyReport) {
    List<DeviceReportView> result = new ArrayList<>();
    int count = 0;

    while (count * 24 < hourlyReport.size()) {
      int startCount = count * 24;
      int endCount = (count + 1) * 24 - 1;
      endCount = endCount > hourlyReport.size() ? hourlyReport.size() - 1 : endCount;

      hourlyToDaily(result, hourlyReport.subList(startCount, endCount));

      count++;
    }

    return result;
  }

  /**
   * 将以小时为单位的统计数据，merge成一条数据。
   * 最多24小时的24条数据.
   *
   * @param result
   * @param hourlyReport
   */
  private static void hourlyToDaily(List<DeviceReportView> result, List<DeviceReport>
      hourlyReport) {

    DeviceReportView view = new DeviceReportView();
    hourlyReport.stream().forEach(
        userReport -> {
          view.setIncreaseNumber(view.getIncreaseNumber() + userReport.getIncreaseNumber());
          view.setTotalNumber(userReport.getTotalNumber());
          view.setActiveNumber(userReport.getActiveNumber());
        }
    );
    view.setDate(hourlyReport.get(0).getStartTime() / 1000); //换算成秒

    result.add(view);
  }

}

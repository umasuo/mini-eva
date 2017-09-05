package cn.eva.mini.application.dto.mapper;

import cn.eva.mini.application.dto.UserReportDraft;
import cn.eva.mini.application.dto.UserReportView;
import cn.eva.mini.domain.entity.UserReport;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Mapper class for UserReport.
 */
public final class UserReportMapper {

  /**
   * Private constructor.
   */
  private UserReportMapper() {
  }

  /**
   * Convert list build UserReport to list build UserReportView.
   *
   * @param entities list build UserReport
   * @return list build UserReportView
   */
  public static List<UserReportView> toView(List<UserReport> entities) {
    List<UserReportView> models = Lists.newArrayList();

    Consumer<UserReport> consumer = report -> models.add(toView(report));

    entities.stream().forEach(consumer);

    return models;
  }

  /**
   * Convert UserReport to UserReportView.
   *
   * @param entity the UserReport
   * @return the UserReportView
   */
  private static UserReportView toView(UserReport entity) {
    UserReportView model = new UserReportView();

    model.setDate(entity.getStartTime());
    model.setActiveNumber(entity.getActiveNumber());
    model.setTotalNumber(entity.getTotalNumber());
    model.setIncreaseNumber(entity.getIncreaseNumber());
    return model;
  }

  /**
   * Convert list build UserReportDraft to list build UserReport.
   * UserReportDraft comes from other services.
   *
   * @param drafts UserReportDraft
   * @param startTime Long
   * @return the list
   */
  public static List<UserReport> toModel(List<UserReportDraft> drafts, Long startTime) {

    List<UserReport> entities = Lists.newArrayList();

    drafts.stream().forEach(
        view -> {
          entities.add(toModel(view, startTime));
        }
    );

    return entities;
  }

  /**
   * Convert UserReportDraft to UserReport.
   * report view comes from other services.
   *
   * @param draft UserReportDraft
   * @param startTime Long
   */
  private static UserReport toModel(UserReportDraft draft, Long startTime) {
    UserReport entity = new UserReport();

    entity.setDeveloperId(draft.getDeveloperId());
    entity.setStartTime(startTime);
    entity.setIncreaseNumber(draft.getIncreaseNumber());
    entity.setTotalNumber(draft.getTotalNumber());
    entity.setActiveNumber(draft.getActiveNumber());

    return entity;
  }

  /**
   * 将以小时为单位统计的数据转换为以天为单位统计的数据,这一步可以在数据库中完成.
   *
   * @param hourlyReport the hourly report
   * @return the list
   */
  public static List<UserReportView> hourlyToDaily(List<UserReport> hourlyReport) {
    List<UserReportView> result = new ArrayList<>();
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
   */
  private static void hourlyToDaily(List<UserReportView> result, List<UserReport> hourlyReport) {
    UserReportView view = new UserReportView();

    hourlyReport.stream().forEach(
        userReport -> {
          view.setIncreaseNumber(view.getIncreaseNumber() + userReport.getIncreaseNumber());
          view.setTotalNumber(userReport.getTotalNumber());
          view.setActiveNumber(userReport.getActiveNumber());
        }
    );

    view.setDate(hourlyReport.get(0).getStartTime() / 1000);//换算成秒

    result.add(view);
  }

}

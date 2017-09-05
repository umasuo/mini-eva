package cn.eva.mini.application.dto;

import cn.eva.mini.infra.enums.FeedbackStatus;
import cn.eva.mini.infra.enums.FeedbackType;
import lombok.Data;

import java.util.List;

/**
 * Feedback view.
 */
@Data
public class FeedbackView {

  /**
   * Uuid.
   */
  private String id;

  /**
   * The Created at.
   */
  private Long createdAt;

  /**
   * The Last modified at.
   */
  private Long lastModifiedAt;

  /**
   * The version.
   */
  private long version;

  /**
   * 用户ID.
   */
  private String userId;

  /**
   * 开发者ID.
   */
  private String developerId;

  /**
   * 设备ID.
   */
  private String deviceId;

  /**
   * 反馈状态.
   */
  private FeedbackStatus developerStatus;

  /**
   * 用户的状态.
   */
  private FeedbackStatus userStatus;

  /**
   * 反馈种类：感谢类，疑问类，错误类，投诉类等
   */
  private FeedbackType type;

  /**
   * 反馈内容.
   */
  private List<ContentView> contents;

  /**
   * The title.
   */
  private String title;

  /**
   * The phone.
   */
  private String phone;

  /**
   * The email.
   */
  private String email;
}

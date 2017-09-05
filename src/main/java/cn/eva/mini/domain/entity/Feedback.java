package cn.eva.mini.domain.entity;

import cn.eva.mini.infra.enums.FeedbackStatus;
import cn.eva.mini.infra.enums.FeedbackType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Feedback.
 */
@Data
@Entity
@Table(name = "feedback")
@EntityListeners(AuditingEntityListener.class)
public class Feedback {

  /**
   * Uuid.
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  private String id;

  /**
   * The Created at.
   */
  @CreatedDate
  @Column(name = "created_at")
  private Long createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  private Long lastModifiedAt;

  /**
   * Feedback version.
   */
  @Version
  private Long version;

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
  @Enumerated(EnumType.STRING)
  private FeedbackType type;

  /**
   * Contents.
   */
  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  @OrderBy("created_at ASC")
  private List<Content> contents;

  /**
   * Title.
   */
  private String title;

  /**
   * Phone.
   */
  private String phone;

  /**
   * Email
   */
  private String email;

}

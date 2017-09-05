package cn.eva.mini.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User report data.
 */
@Data
@Entity
@Table(name = "user_report")
public class UserReport {

  /**
   * Uuid.
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  private String id;

  /**
   * The Developer id.
   */
  private String developerId;

  /**
   * 统计的那个小时的起始时间戳.
   */
  private Long startTime;

  /**
   * 这个小时内新增的用户数量.
   */
  private int increaseNumber;

  /**
   * 截止目前，当天的活跃的用户数.
   */
  private int activeNumber;

  /**
   * 截止当前小时，总的用户注册数
   */
  private int totalNumber;

}

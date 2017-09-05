package cn.eva.mini.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Device report datas.
 */
@Data
@Entity
@Table(name = "device_report")
public class DeviceReport {
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
   * The Device definition id.
   */
  private String deviceDefinitionId;

  /**
   * start time build it's hour.
   */
  private Long startTime;

  /**
   * The new increase device number.
   */
  private int increaseNumber;

  /**
   * The Online number.
   */
  private int activeNumber;

  /**
   * total number build device at this hour.
   */
  private int totalNumber;
}

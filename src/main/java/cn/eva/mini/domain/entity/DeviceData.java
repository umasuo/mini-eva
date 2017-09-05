package cn.eva.mini.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Device Data
 */
@Entity
@Table(name = "device_data")
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class DeviceData {

  /**
   * Id.
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
  protected Long createdAt;

  /**
   * The Last modified at.
   * Maybe we do not need this.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  protected Long lastModifiedAt;

  /**
   * version used for update date check.
   */
  @Version
  private Integer version;

  /**
   * data definition id, used to check if the data is in the correct structure.
   */
  private String dataDefinitionId;

  /**
   * device id.
   */
  private String deviceId;

  /**
   * the real structured json data.
   */
  @Column(length = 65535)
  private String data;

  // the next several filed is redundancy for search or process.

  /**
   * User id.
   */
  private String userId;

  /**
   * Developer id.
   */
  private String developerId;

  /**
   * Device definition id.
   */
  private String deviceDefinitionId;

}

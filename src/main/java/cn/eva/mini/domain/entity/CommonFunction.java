package cn.eva.mini.domain.entity;

import cn.eva.mini.application.dto.function.FunctionDataType;
import cn.eva.mini.infra.db.postgres.dialect.JSONBUserType;
import cn.eva.mini.infra.enums.TransferType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
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
 * 预先定义好的设备功能.
 */
@Entity
@Table(name = "common_function")
@Data
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "dataType", typeClass = JSONBUserType.class, parameters = {
    @Parameter(name = JSONBUserType.CLASS, value = "cn.eva.mini.application.dto.function.FunctionDataType")})
public class CommonFunction {

  /**
   * id.
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
   * 短功能ID, 每种设备对应的功能ID唯一.
   */
  private String functionId;

  /**
   * 功能名字，用于展示，同一个产品唯一。
   */
  private String name;

  /**
   * 功能具体介绍。
   */
  private String description;

  /**
   * 功能的具体数据.
   */
  @Type(type = "dataType")
  private FunctionDataType dataType;

  /**
   * 传输类型.
   */
  private TransferType transferType;
}

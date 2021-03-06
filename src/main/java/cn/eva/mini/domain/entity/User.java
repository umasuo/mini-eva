package cn.eva.mini.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * User entity.
 * TODO should we give a numerical id?
 */
@Entity
@Table(name = "users")
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {

  /**
   * unique id, 这个ID等于是用户的open ID.
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
   * version used for update date check.
   */
  @Version
  private Integer version;

  /**
   * user's email, this should be unique.
   */
  @Column(unique = true, nullable = true)
  private String email;

  /**
   * user's mobile phone.
   */
  @Column(unique = true, nullable = true)
  private String phone;

  /**
   * user's externalId.
   * if the user is sign up with another OAUTH2 provider, this will be the.
   * this will be used only then they bind to the platform account.
   */
  @Column(unique = true, nullable = true)
  private String externalId;

  /**
   * which developer this user belong to.
   */
  @Column(nullable = false)
  private String developerId;

  /**
   * user's password.
   */
  private String password;

  /**
   * user detail info.
   */
  @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  private UserDetailInfo userDetail;

  /**
   * To string method.
   *
   * @return
   */
  @Override
  public String toString() {
    return "User{"
      + "id='" + id + '\''
      + ", createdAt=" + createdAt
      + ", lastModifiedAt=" + lastModifiedAt
      + ", version=" + version
      + ", externalId='" + externalId + '\''
      + ", developerId='" + developerId + '\''
      + ", userDetail=" + userDetail
      + '}';
  }
}

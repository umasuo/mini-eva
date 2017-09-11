package cn.eva.mini.application.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * User Sign in model.
 */
@Data
public class UserLogin implements Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -1226855896214404115L;

  /**
   * user's email. unique on this platform.
   */
  @Email
  private String email;

  /**
   * user's mobile phone. unique on this platform.
   */
  @NotNull
  private String phone;

  /**
   * developer id.
   */
  @NotNull
  private String developerId;

  /**
   * Which device this user from.
   */
  private String deviceDefinitionId;

  /**
   * user's externalId.
   */
  private String externalId;

  /**
   * ^                 # start-of-string
   * (?=.*[0-9])       # a digit must occur at least once
   * (?=.*[a-z])       # a lower case letter must occur at least once
   * (?=\S+$)          # no whitespace allowed in the entire string
   * .{8,}             # anything, at least eight places though
   * $                 # end-of-string
   */
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")
  @NotNull
  private String password;

  /**
   * To string method, don't print password.
   *
   * @return
   */
  @Override
  public String toString() {
    return "UserLogin{"
      + "email='" + email + '\''
      + ", phone='" + phone + '\''
      + ", developerId='" + developerId + '\''
      + ", deviceDefinitionId='" + deviceDefinitionId + '\''
      + ", externalId='" + externalId + '\''
      + '}';
  }
}

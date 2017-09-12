package cn.eva.mini.application.dto.user;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Register info.
 */
@Data
public class UserRegisterInfo implements Serializable {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = 2333151638836537082L;

  /**
   * Phone.
   */
  @NotNull
  private String phone;

  /**
   * Sms code.
   */
  @NotNull
  private String smsCode;

  /**
   * Password.
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
   * Developer id.
   */
  private String developerId;
}

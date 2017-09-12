package cn.eva.mini.application.dto.developer;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Developer register info.
 */
@Data
public class DeveloperRegister implements Serializable {

  /**
   * Auto generated serial version uid.
   */
  private static final long serialVersionUID = 3911397336752429079L;

  /**
   * email of the developer,
   */
  @Email
  private String email;

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

}

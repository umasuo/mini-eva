package cn.eva.mini.application.dto.developer;

import lombok.Data;

import java.io.Serializable;

/**
 * Developer login result.
 */
@Data
public class DeveloperLoginResult implements Serializable {
  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = 3640903092407994272L;

  /**
   * Developer view.
   */
  private DeveloperView developerView;

  /**
   * Token.
   */
  private String token;


  /**
   * constructor with parameters.
   *
   * @param developerView
   * @param token
   */
  public DeveloperLoginResult(DeveloperView developerView, String token) {
    this.developerView = developerView;
    this.token = token;
  }

  /**
   * Getter.
   *
   * @return
   */
  public DeveloperView getDeveloperView() {
    return developerView;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setDeveloperView(DeveloperView developerView) {
    this.developerView = developerView;
  }

  /**
   * Getter.
   *
   * @return
   */
  public String getToken() {
    return token;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setToken(String token) {
    this.token = token;
  }


}

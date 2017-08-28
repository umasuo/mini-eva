package cn.eva.mini.infra.auth;

import cn.eva.mini.infra.exception.AuthFailedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.List;

/**
 * This provider provide an tool to check the authentication of the token.
 * default policy provider.
 */
public interface AuthPolicyProvider {

  /**
   * check the authentication of the token
   *
   * @param token     token input.
   */
  default void checkToken(String token) {
    //todo
  }

}

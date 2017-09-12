package cn.eva.mini.infra.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 权限验证第一步，这里只验证用户是否已经登陆，并获取其具体权限信息，将开发者ID，权限通过header传入具体service.
 */
//@Component
public class AuthenticationPreFilter implements Filter {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationPreFilter.class);





  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    LOGGER.info("In auth filter init....");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      LOGGER.info("In auth filter runner....");
      chain.doFilter(request,response);
  }

  @Override
  public void destroy() {
    LOGGER.info("In auth filter destroy....");
  }
}

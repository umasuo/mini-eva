package cn.eva.mini.infra.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auth filter configuration.
 * Set the url patterns for the auth filter.
 */
@Configuration
public class AuthFilterConfiguration {

  @Bean
  public FilterRegistrationBean someFilterRegistration() {

    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new AuthenticationPreFilter());
    registration.addUrlPatterns("/api/*");
//    registration.addInitParameter("paramName", "paramValue");
    registration.setName("apiAuthFilter");
    registration.setOrder(1);
    return registration;
  }

}

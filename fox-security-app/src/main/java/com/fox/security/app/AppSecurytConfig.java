package com.fox.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.fox.core.properties.SecurityConstants;
import com.fox.core.properties.SecurityProperties;

@Configuration
public class AppSecurytConfig extends WebSecurityConfigurerAdapter {


  @Autowired
  private SecurityProperties securityProperties;


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http//
      .httpBasic()
        .and()//
        .authorizeRequests()//
        .antMatchers(//
            SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, //
            SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, //
            securityProperties.getBrowser().getLoginPage(), //
            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*"//
            , "/error"//
        )//
        .permitAll()//
        .anyRequest()//
        .authenticated()//
        .and()//
        .csrf().disable()//
    ;
  }

}

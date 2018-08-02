package com.fox.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fox.core.properties.SecurityProperties;

@Configuration
public class BrowserSecurytConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private SecurityProperties securityProperties;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http//
        // .httpBasic()//
        .formLogin()//
        .loginPage("/authentication/require")//
        .loginProcessingUrl("/authentication/form")//
        .and()//
        .authorizeRequests()//
//        .antMatchers("/fox-signin.html")//
        .antMatchers(//
            "/authentication/require",//
            securityProperties.getBrowser().getLoginPage())//
        .permitAll()//
        .anyRequest()//
        .authenticated()//
        .and().csrf().disable()//
    ;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

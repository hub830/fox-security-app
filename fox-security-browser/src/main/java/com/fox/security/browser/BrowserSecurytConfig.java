package com.fox.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fox.core.properties.SecurityProperties;
import com.fox.core.validate.code.ValidateCodeFilter;
import com.fox.security.browser.authentication.FoxAuthenticationFailHandler;
import com.fox.security.browser.authentication.FoxAuthenticationSuccessHandler;

@Configuration
public class BrowserSecurytConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private SecurityProperties securityProperties;

  @Autowired
  private FoxAuthenticationSuccessHandler foxAuthenticationSuccessHandler;

  @Autowired
  private FoxAuthenticationFailHandler foxAuthenticationFailHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
    validateCodeFilter.setAuthenticationFailureHandler(foxAuthenticationFailHandler);
    http//
        .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)//
        // .httpBasic()//
        .formLogin()//
        .loginPage("/authentication/require")//
        .loginProcessingUrl("/authentication/form")//
        .successHandler(foxAuthenticationSuccessHandler)//
        .failureHandler(foxAuthenticationFailHandler)//
        .and()//
        .authorizeRequests()//
        // .antMatchers("/fox-signin.html")//
        .antMatchers(//
            "/authentication/require", //
            "/error", //
            "/code/image", //
            securityProperties.getBrowser().getLoginPage()//
        )//
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

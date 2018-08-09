package com.fox.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeAuthenticationSecurityConfig
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  @Autowired
  private AuthenticationSuccessHandler foxAuthenticationSuccessHandler;

  @Autowired             
  private AuthenticationFailureHandler foxAuthenticationFailureHandler;

  @Autowired             
  private UserDetailsService userDetailsService;
  
  @Override
  public void configure(HttpSecurity http) throws Exception {
    SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter ();
    AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
    smsCodeAuthenticationFilter.setAuthenticationManager(authenticationManager); 
    smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(foxAuthenticationSuccessHandler);
    smsCodeAuthenticationFilter.setAuthenticationFailureHandler(foxAuthenticationFailureHandler);
    
    SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
    smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
    
    http.authenticationProvider(smsCodeAuthenticationProvider);
    http.addFilterAfter(smsCodeAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
    
  }

  
}

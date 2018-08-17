package com.fox.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;
import com.fox.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.fox.core.properties.SecurityConstants;
import com.fox.core.properties.SecurityProperties;
import com.fox.core.validate.code.ValidateCodeSecurityConfig;

//@Configuration
public class AppSecurytConfig extends WebSecurityConfigurerAdapter {


  @Autowired
  private SecurityProperties securityProperties;

  
  @Autowired
  protected AuthenticationSuccessHandler foxAuthenticationSuccessHandler;
  
  @Autowired
  protected AuthenticationFailureHandler foxAuthenticationFailureHandler;

  @Autowired
  private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
  
  
  @Autowired
  private ValidateCodeSecurityConfig validateCodeSecurityConfig;
  
  @Autowired
  private SpringSocialConfigurer imoocSocialSecurityConfig;
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.formLogin()
        .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
        .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
        .successHandler(foxAuthenticationSuccessHandler)
        .failureHandler(foxAuthenticationFailureHandler);
    
    http.apply(validateCodeSecurityConfig)
            .and()
        .apply(smsCodeAuthenticationSecurityConfig)
            .and()
        .apply(imoocSocialSecurityConfig)
            .and()
        .authorizeRequests()
            .antMatchers(
                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                securityProperties.getBrowser().getLoginPage(),
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                securityProperties.getBrowser().getSignUpUrl(),
//                securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                securityProperties.getBrowser().getSignOutUrl(),
                "/user/regist")
                .permitAll()
            .anyRequest()
            .authenticated()
            .and()
        .csrf().disable();
  }

}

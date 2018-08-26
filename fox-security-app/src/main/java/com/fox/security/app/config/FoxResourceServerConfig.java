package com.fox.security.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.fox.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.fox.core.properties.SecurityConstants;
import com.fox.core.properties.SecurityProperties;
import com.fox.core.validate.code.ValidateCodeSecurityConfig;

@Configuration
@EnableResourceServer
public class FoxResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Autowired
  protected AuthenticationSuccessHandler foxAuthenticationSuccessHandler;

  @Autowired
  protected AuthenticationFailureHandler foxAuthenticationFailureHandler;

  @Autowired
  private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

  @Autowired
  private ValidateCodeSecurityConfig validateCodeSecurityConfig;


  @Autowired
  private SecurityProperties securityProperties;

  @Override
  public void configure(HttpSecurity http) throws Exception {

    http//
        .formLogin()//
        .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)//
        .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)//
        .successHandler(foxAuthenticationSuccessHandler)//
        .failureHandler(foxAuthenticationFailureHandler);

    http// .apply(validateCodeSecurityConfig)
        // .and()
        .apply(smsCodeAuthenticationSecurityConfig)//
        .and()//
        // .apply(imoocSocialSecurityConfig)//
        // .and()//
        .authorizeRequests()//
        .antMatchers(//
            SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, //
            SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, //
            securityProperties.getBrowser().getLoginPage(), //
            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*", //
            securityProperties.getBrowser().getSignUpUrl(), //
            securityProperties.getBrowser().getSession().getSessionInvalidUrl(), //
            securityProperties.getBrowser().getSignOutUrl(), "/user/regist")//
        .permitAll()//
        .anyRequest()//
        .authenticated()//
        .and().csrf().disable();//
  }
  /*
   * @Override public void configure(final HttpSecurity http) throws Exception { http// .csrf()//
   * .disable()// .authorizeRequests()// .anyRequest()// .authenticated(); }
   */
}

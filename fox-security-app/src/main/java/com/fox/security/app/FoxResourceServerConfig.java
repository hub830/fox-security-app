package com.fox.security.app;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import com.fox.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.fox.core.properties.SecurityConstants;
import com.fox.core.properties.SecurityProperties;
import com.fox.core.validate.code.ValidateCodeSecurityConfig;

@Configuration
@EnableResourceServer
public class FoxResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Autowired
  private AuthenticationSuccessHandler foxAuthenticationSuccessHandler;

  @Autowired
  private AuthenticationFailureHandler foxAuthenticationFailureHandler;

  @Autowired
  private SecurityProperties securityProperties;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

  @Autowired
  private ValidateCodeSecurityConfig validateCodeSecurityConfig;

  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
    tokenRepository.setDataSource(dataSource);
    // tokenRepository.setCreateTableOnStartup(true);
    return tokenRepository;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // applyPasswordAuthenticationConfig(http);
    http//
        .formLogin()//
        .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)//
        .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)//
        .successHandler(foxAuthenticationSuccessHandler)//
        .failureHandler(foxAuthenticationFailureHandler)//
    ;
    http//
        .apply(validateCodeSecurityConfig)//
        .and()//
        .apply(smsCodeAuthenticationSecurityConfig)//
        .and()//
        .rememberMe()//
        .tokenRepository(persistentTokenRepository())//
        .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())//
        .userDetailsService(userDetailsService)//
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

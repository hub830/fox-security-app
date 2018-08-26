package com.fox.security.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class FoxAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;


  @Override
  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints//
        .authenticationManager(authenticationManager)//
    ;
  }

  @Override
  public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
    String secret = passwordEncoder.encode("secret");
    clients//
        .inMemory()//
        .withClient("client")//
        .secret(passwordEncoder.encode("secret"))//
//        .secret("secret")//
        .authorizedGrantTypes("password", "refresh_token")//
        .scopes("read", "write")//
    ;
  }

}

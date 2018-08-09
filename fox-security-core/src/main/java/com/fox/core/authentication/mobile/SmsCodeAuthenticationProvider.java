package com.fox.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import lombok.Setter;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

  @Setter
  private UserDetailsService userDetailsService;
  
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    SmsCodeAuthenticationToken   authenticationToken = (SmsCodeAuthenticationToken)authentication;
    UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
    if(user==null)
    {
      throw new InternalAuthenticationServiceException("无法获取 用户信息");
    }
    SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user,user.getAuthorities());
    authenticationResult.setDetails(authenticationToken.getDetails());
    return authenticationResult;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    if (SmsCodeAuthenticationToken.class.isAssignableFrom(authentication)) {
      return true;
    }
    return false;
  }

}

package com.fox.security.app.authentication;

import java.io.IOException;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.fox.core.properties.LoginType;
import com.fox.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FoxAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  @Autowired
  private SecurityProperties securityProperties;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  
  @Autowired
  private ClientDetailsService clientDetailsService;
  
  @Autowired
  private AuthorizationServerTokenServices authorizationServerTokenServices;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    log.info("登录成功");


    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Basic ")) {
      throw new UnapprovedClientAuthenticationException("请求头中无client信息");
    }

      String[] tokens = extractAndDecodeHeader(header, request);
      assert tokens.length == 2;

      String clientId = tokens[0];
      String clientSecret = tokens[1];
      ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
      
      if(clientDetails ==null)
      {
        throw new UnapprovedClientAuthenticationException("clientId对应的配置就算不存在："+clientId);
      }
      clientSecret = passwordEncoder.encode(clientSecret);
      if( passwordEncoder.matches(clientSecret, clientDetails.getClientSecret()))
      {
        throw new UnapprovedClientAuthenticationException("clientSecret不匹配："+clientId);
      }
      TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

      OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
      OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication); 
      OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
      
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(JSON.toJSONString(accessToken));
  }

  private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
      throws IOException {

    byte[] base64Token = header.substring(6).getBytes("UTF-8");
    byte[] decoded;
    try {
      decoded = Base64.getDecoder().decode(base64Token);
    } catch (IllegalArgumentException e) {
      throw new BadCredentialsException("Failed to decode basic authentication token");
    }

    String token = new String(decoded, "UTF-8");

    int delim = token.indexOf(":");

    if (delim == -1) {
      throw new BadCredentialsException("Invalid basic authentication token");
    }
    return new String[] {token.substring(0, delim), token.substring(delim + 1)};
  }

}

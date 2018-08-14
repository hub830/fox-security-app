package com.fox.security.app.authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    log.info("µÇÂ¼³É¹¦");
    if (securityProperties.getBrowser().getLoginType() == LoginType.JSON) {
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(JSON.toJSONString(authentication));
    }else
    {
      super.onAuthenticationSuccess(request, response, authentication);
    }
  }

}

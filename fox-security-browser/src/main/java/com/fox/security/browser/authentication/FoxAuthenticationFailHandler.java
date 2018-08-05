package com.fox.security.browser.authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.fox.core.properties.LoginType;
import com.fox.core.properties.SecurityProperties;
import com.fox.security.browser.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FoxAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

  @Autowired
  private SecurityProperties securityProperties;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    log.info("µÇÂ¼Ê§°Ü");
    if (LoginType.JSON == securityProperties.getBrowser().getLoginType()) {
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      response.getWriter().write(JSON.toJSONString(new SimpleResponse(exception.getMessage())));
    } else {
      super.onAuthenticationFailure(request, response, exception);
    }
  }

}

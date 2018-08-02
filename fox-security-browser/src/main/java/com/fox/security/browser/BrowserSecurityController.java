package com.fox.security.browser;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fox.core.properties.SecurityProperties;
import com.fox.security.browser.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BrowserSecurityController {

  private RequestCache requestCache = new HttpSessionRequestCache();

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Autowired
  private SecurityProperties securityProperties;

  /**
   * 当需要身份认证时跳转到这里
   * 
   * @param request
   * @param response
   * @return
   * @throws IOException
   */
  @RequestMapping("/authentication/require")
  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  public ResponseEntity<SimpleResponse> requireAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    SavedRequest savedRequest = requestCache.getRequest(request, response);
    if (savedRequest != null) {
      String targetUrl = savedRequest.getRedirectUrl();
      log.info("引发跳转的请求是:{}", targetUrl);
      if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
        redirectStrategy.sendRedirect(request, response,
            securityProperties.getBrowser().getLoginPage());
      }
    }
    SimpleResponse simpleResponse = new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    return new ResponseEntity<SimpleResponse>(simpleResponse, HttpStatus.UNAUTHORIZED);
  }
}

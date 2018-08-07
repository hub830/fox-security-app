package com.fox.core.validate.code;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fox.core.properties.SecurityProperties;
import lombok.Setter;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

  @Setter
  private AuthenticationFailureHandler authenticationFailureHandler;

  private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

  private AntPathMatcher pathMatcher = new AntPathMatcher();
  @Setter
  private SecurityProperties securityProperties;

  private Set<String> urls = new HashSet<>();

  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();
    String[] configUrls = StringUtils.split(securityProperties.getCode().getImage().getUrl(), ",");
    if (ArrayUtils.isNotEmpty(configUrls)) {
      for (String configUrl : configUrls) {
        urls.add(configUrl);
      }
    }
    urls.add("/authentication/form");
  }



  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    boolean action = false;
    for (String url : urls) {
      if (pathMatcher.match(url, request.getRequestURI())) {
        action = true;
      }
    }

    if (action) {
      try {
        validate(new ServletWebRequest(request, response));
      } catch (ValidateCodeException e) {
        authenticationFailureHandler.onAuthenticationFailure(request, response, e);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }

  private void validate(ServletWebRequest request)
      throws ValidateCodeException, ServletRequestBindingException {

    ImageCode codeInSession =
        (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);

    String codeInRequest =
        ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

    if (StringUtils.isBlank(codeInRequest)) {
      throw new ValidateCodeException("验证码的值不能为空");
    }

    if (codeInSession == null) {
      throw new ValidateCodeException("验证码不存在");
    }

    if (codeInSession.isExpried()) {
      sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
      throw new ValidateCodeException("验证码已过期");
    }

    if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
      throw new ValidateCodeException("验证码不匹配");
    }

    sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
  }

}

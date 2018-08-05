package com.fox.core.validate.code;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.Setter;

public class ValidateCodeFilter extends OncePerRequestFilter {

  @Setter
  private AuthenticationFailureHandler authenticationFailureHandler;

  private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (StringUtils.equals("/authentication/form", request.getRequestURI())
        && StringUtils.equals("POST", request.getMethod())) {
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
      throw new ValidateCodeException("��֤���ֵ����Ϊ��");
    }

    if (codeInSession == null) {
      throw new ValidateCodeException("��֤�벻����");
    }

    if (codeInSession.isExpried()) {
      sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
      throw new ValidateCodeException("��֤���ѹ���");
    }

    if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
      throw new ValidateCodeException("��֤�벻ƥ��");
    }

    sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
  }

}

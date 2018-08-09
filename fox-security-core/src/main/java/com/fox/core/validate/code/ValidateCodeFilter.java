package com.fox.core.validate.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fox.core.properties.SecurityConstants;
import com.fox.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

  /**
   * 验证码校验失败处理器
   */
  @Autowired
  private AuthenticationFailureHandler authenticationFailureHandler;
  /**
   * 验证请求url与配置的url是否匹配的工具类
   */
  private AntPathMatcher pathMatcher = new AntPathMatcher();
  /**
   * 系统中的验证码处理器
   */
  @Autowired
  private ValidateCodeProcessorHolder validateCodeProcessorHolder;
  /**
   * 系统配置信息
   */
  @Autowired
  private SecurityProperties securityProperties;
  /**
   * 存放所有需要校验码的url
   */
  private Map<String, ValidateCodeType> urlMap = new HashMap<>();

  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();
    urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
    addUrlMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);
    urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
    addUrlMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);

  }

  /**
   * 将系统 配置的需要校验码的url根据校验的类型放入map
   * 
   * @param url
   * @param type
   */
  private void addUrlMap(String url, ValidateCodeType type) {
    String[] configUrls = StringUtils.split(url, ",");
    if (ArrayUtils.isNotEmpty(configUrls)) {
      for (String configUrl : configUrls) {
        urlMap.put(configUrl, type);
      }
    }
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    ValidateCodeType type = getValidateCodeType(request);
    if (type != null) {
      log.info("校验请求({})中的验证码,验证码类型{}", request.getRequestURI(), type);
      ValidateCodeProcessor processor = validateCodeProcessorHolder.findValidateCodeProcessor(type);
      try {
        processor.validate(new ServletWebRequest(request, response));
      } catch (ValidateCodeException e) {
        authenticationFailureHandler.onAuthenticationFailure(request, response, e);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }

  private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
    ValidateCodeType result = null;
    if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
      Set<String> urls = urlMap.keySet();
      for (String url : urls) {
        if (pathMatcher.match(url, request.getRequestURI())) {
          result = urlMap.get(url);
        }
      }
    }
    return result;
  }
}

package com.fox.security.browser.validate.code.impl;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import com.fox.core.validate.code.ValidateCode;
import com.fox.core.validate.code.ValidateCodeRepository;
import com.fox.core.validate.code.ValidateCodeType;

@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

  String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
  /**
   * 操作session的工具类
   */
  private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

  @Override
  public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
    sessionStrategy.setAttribute(request, getSessionKey(request, type), code);
  }

  @Override
  public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {

    return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request, type));

  }

  @Override
  public void remove(ServletWebRequest request, ValidateCodeType type) {

    sessionStrategy.removeAttribute(request, getSessionKey(request, type));

  }

  /**
   * 构建验证码放入session时的key
   * 
   * @param request
   * @return
   */
  private String getSessionKey(ServletWebRequest request, ValidateCodeType type) {
    return SESSION_KEY_PREFIX + type.toString().toUpperCase();
  }
}

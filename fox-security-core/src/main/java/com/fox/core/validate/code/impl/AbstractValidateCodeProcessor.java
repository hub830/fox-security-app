package com.fox.core.validate.code.impl;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;
import com.fox.core.validate.code.ValidateCodeGenerator;
import com.fox.core.validate.code.ValidateCodeProcessor;

public abstract class AbstractValidateCodeProcessor<C> implements ValidateCodeProcessor {

  private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

  @Autowired
  private Map<String, ValidateCodeGenerator> validateCodeGenerators;

  @Override
  public void create(ServletWebRequest request) throws Exception {
    C validateCode = generate(request);
    save(request, validateCode);
    send(request, validateCode);

  }

  protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

  private void save(ServletWebRequest request, C validateCode) {
    sessionStrategy.setAttribute(request,
        SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(), validateCode);
  }

  @SuppressWarnings("unchecked")
  private C generate(ServletWebRequest request) {

    String type = getProcessorType(request);
    ValidateCodeGenerator validateCodeGenerator =
        validateCodeGenerators.get(type + "CodeGenerator");
    return (C) validateCodeGenerator.generate(request);
  }

  private String getProcessorType(ServletWebRequest request) {
    return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
  }

}

package com.fox.core.validate.code.impl;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import com.fox.core.validate.code.ValidateCode;
import com.fox.core.validate.code.ValidateCodeException;
import com.fox.core.validate.code.ValidateCodeGenerator;
import com.fox.core.validate.code.ValidateCodeProcessor;
import com.fox.core.validate.code.ValidateCodeRepository;
import com.fox.core.validate.code.ValidateCodeType;

public abstract class AbstractValidateCodeProcessor<C extends ValidateCode>
    implements ValidateCodeProcessor {
  @Autowired
  private ValidateCodeRepository validateCodeRepository;

  /**
   * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现
   */
  @Autowired
  private Map<String, ValidateCodeGenerator> validateCodeGenerators;

  /**
   * @see com.imooc.security.core.validate.code.ValidateCodeProcessor#create(org.
   *      springframework.web.context.request.ServletWebRequest)
   */
  @Override
  public void create(ServletWebRequest request) throws Exception {
    C validateCode = generate(request);
    save(request, validateCode);
    send(request, validateCode);

  }

  /**
   * 发送校验码，由子类实现
   * 
   * @param request
   * @param validateCode
   * @throws Exception
   */
  protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

  /**
   * 保存校验码
   * 
   * @param request
   * @param validateCode
   */
  private void save(ServletWebRequest request, C validateCode) {
    ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
    validateCodeRepository.save(request, code, getValidateCodetype(request));
  }

  /**
   * 生成校验码
   * 
   * @param request
   * @return
   */
  @SuppressWarnings("unchecked")
  private C generate(ServletWebRequest request) {

    String type = getValidateCodetype(request).toString().toLowerCase();
    String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
    ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
    if (validateCodeGenerator == null) {
      throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
    }
    return (C) validateCodeGenerator.generate(request);
  }

  /**
   * 根据请求的Url获取 校验码的类型
   * 
   * @param request
   * @return
   */
  private ValidateCodeType getValidateCodetype(ServletWebRequest request) {
    String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
    return ValidateCodeType.valueOf(type.toUpperCase());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void validate(ServletWebRequest request) {
    ValidateCodeType codeType = getValidateCodetype(request);

    C codeInSession = (C) validateCodeRepository.get(request, codeType);

    String codeInRequest;

    try {
      codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
          codeType.getParamNameOnValidate());
    } catch (ServletRequestBindingException e) {

      throw new ValidateCodeException("获取验证码的值失败");
    }
    if (StringUtils.isBlank(codeInRequest)) {
      throw new ValidateCodeException(codeType + "验证码的值不能为空");
    }
    if (codeInSession == null) {
      throw new ValidateCodeException(codeType + "验证码不存在");
    }
    if (codeInSession.isExpried()) {
      validateCodeRepository.remove(request, codeType);
      throw new ValidateCodeException(codeType + "验证码己过期");
    }
    if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
      throw new ValidateCodeException(codeType + "验证码不匹配");
    }
    validateCodeRepository.remove(request, codeType);
  }


}

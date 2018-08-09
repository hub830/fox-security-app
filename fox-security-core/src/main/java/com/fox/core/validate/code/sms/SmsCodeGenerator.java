package com.fox.core.validate.code.sms;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import com.fox.core.properties.SecurityProperties;
import com.fox.core.validate.code.ValidateCode;
import com.fox.core.validate.code.ValidateCodeGenerator;

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

  @Autowired
  private SecurityProperties securityProperties;

  @Override
  public ValidateCode generate(ServletWebRequest request) {
    String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
    return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
  }
}

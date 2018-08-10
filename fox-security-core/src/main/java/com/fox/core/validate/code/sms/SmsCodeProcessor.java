package com.fox.core.validate.code.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import com.fox.core.validate.code.ValidateCode;
import com.fox.core.validate.code.impl.AbstractValidateCodeProcessor;

@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

  @Autowired
  private SmsCodeSender smsCodeSender;

  @Override
  protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
    String mobile = ServletRequestUtils.getStringParameter(request.getRequest(), "mobile");
    smsCodeSender.send(mobile, validateCode.getCode());
  }

}
package com.fox.core.validate.code;

import java.util.Map;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateCodeProcessorHolder {
  @Autowired
  Map<String,ValidateCodeProcessor> validateCodeProcessors;
  
  public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type)
  {
    return findValidateCodeProcessor(type.toString().toLowerCase());
  }

  public ValidateCodeProcessor findValidateCodeProcessor(String type) {
    String name = type.toLowerCase()+ValidateCodeProcessor.class.getSimpleName();
    ValidateCodeProcessor processor = validateCodeProcessors.get(name);
    if(processor ==null)
    {
      throw new ValidationException("验证码处理器"+name+"不存在");
    }
    return processor;
  }
}

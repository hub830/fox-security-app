package com.fox.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeRepository {

  void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type);

  ValidateCode get(ServletWebRequest request, ValidateCodeType type);

  void remove(ServletWebRequest request, ValidateCodeType type);
}

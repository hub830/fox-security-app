package com.fox.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcessor {
  String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

  /**
   * ����У����
   * 
   * @param request
   * @throws Exception
   */
  void create(ServletWebRequest request) throws Exception;
  
  /**
   * У����֤��
   * @param request
   */
  void validate(ServletWebRequest request);
}

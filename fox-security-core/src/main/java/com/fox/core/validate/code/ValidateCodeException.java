package com.fox.core.validate.code;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {
  private static final long serialVersionUID = 1L;

  public ValidateCodeException(String msg) {
    super(msg);
  }
}

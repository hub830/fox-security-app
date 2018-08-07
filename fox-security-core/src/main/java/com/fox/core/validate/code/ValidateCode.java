package com.fox.core.validate.code;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ValidateCode {
  private String code;
  private LocalDateTime expireTime;

  public ValidateCode(String code, int expireIn) {
    this.code = code;
    this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
  }


  public ValidateCode(String code, LocalDateTime expireTime) {
    this.code = code;
    this.expireTime = expireTime;
  }


  public boolean isExpried() {
    return LocalDateTime.now().isAfter(expireTime);
  }


  public ValidateCode() {
    super();
  }

}

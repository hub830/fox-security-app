package com.fox.core.validate.code;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

@Data
public class ValidateCode {

  private String code;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime expireTime;

  public ValidateCode(String code, int expireIn) {
    this.code = code;
    this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
  }


  public ValidateCode(String code, LocalDateTime expireTime) {
    this.code = code;
    this.expireTime = expireTime;
  }


  @JsonIgnore
  public boolean isExpried() {
    return LocalDateTime.now().isAfter(expireTime);
  }


  public ValidateCode() {
    super();
  }

}

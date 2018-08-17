package com.fox.security.app.validate.code.impl;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import com.fox.core.validate.code.ValidateCode;
import com.fox.core.validate.code.ValidateCodeException;
import com.fox.core.validate.code.ValidateCodeRepository;
import com.fox.core.validate.code.ValidateCodeType;

@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;

  @Override
  public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
    redisTemplate.opsForValue().set(buildKey(request, type), code, 30, TimeUnit.MINUTES);
  }

  @Override
  public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {

    Object value = redisTemplate.opsForValue().get(buildKey(request, type));
    if (value == null) {
      return null;
    }
    return (ValidateCode) value;
  }

  @Override
  public void remove(ServletWebRequest request, ValidateCodeType type) {
    redisTemplate.delete(buildKey(request, type));
  }

  private Object buildKey(ServletWebRequest request, ValidateCodeType type) {
    String deviceId = request.getHeader("deviceId");
    if (StringUtils.isBlank(deviceId)) {
      throw new ValidateCodeException("��������ͷ��Я��deviceId");
    }
    return "code:" + type.toString().toLowerCase() + ":" + deviceId;
  }

}

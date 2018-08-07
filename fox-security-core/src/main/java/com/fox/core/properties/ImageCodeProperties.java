package com.fox.core.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ImageCodeProperties extends SmsCodeProperties {
  private int width = 67;
  private int height = 23;

  public ImageCodeProperties() {
    setLength(4);
  }

}

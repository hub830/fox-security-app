package com.fox.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties(prefix="fox.security")
public class SecurityProperties {
  BrowserProperties browser = new BrowserProperties();
}

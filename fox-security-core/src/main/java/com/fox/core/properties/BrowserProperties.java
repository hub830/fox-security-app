package com.fox.core.properties;

import lombok.Data;

@Data
public class BrowserProperties {
  private String loginPage = "/fox-signIn.html";
  private LoginType loginType = LoginType.JSON;
}

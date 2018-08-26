package com.fox.core.properties;

import lombok.Data;

@Data
public class BrowserProperties {

  private SessionProperties session = new SessionProperties();
  private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
  private LoginType loginType = LoginType.JSON;
  private int rememberMeSeconds = 3600;


  private String signUpUrl = "/fox-signUp.html";
  private String signOutUrl;



}

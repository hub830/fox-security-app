package com.fox.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserQueryCondition {

  private String username;
  
  private int age;
  
  private int ageTo;
  
}

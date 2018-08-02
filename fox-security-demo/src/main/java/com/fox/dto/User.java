package com.fox.dto;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import com.fasterxml.jackson.annotation.JsonView;
import com.fox.validator.MyConstraint;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

  public interface UserSimpleView {
  };

  public interface UserDetailView extends UserSimpleView {
  };

  
  @JsonView(UserSimpleView.class)
  private Long id;
  
  @MyConstraint(message="这是一个测试")
  @JsonView(UserSimpleView.class)
  private String username;

  @NotBlank(message="密码不能为空")
  @JsonView(UserDetailView.class)
  private String password;

  @Past(message="生日必须 为过去的时间")
  @JsonView(UserDetailView.class)
  private Date birthday;
}

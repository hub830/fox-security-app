package com.fox.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyConstraintValidate implements ConstraintValidator<MyConstraint, Object> {

//  @Autowired
//  private HelloService helloService;

  @Override
  public void initialize(MyConstraint constraintAnnotation) {
    log.info("my validator init");
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    log.info("----------- value:{}", value);
//    helloService.greeting("tom");
    return false;
  }

}

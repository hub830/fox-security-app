package com.fox.core.validate.code;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
public class ValidateCodeController {
  @Autowired
  private Map<String,ValidateCodeProcessor> validateCodeProcessors;
  
  

  @GetMapping("/code/{type}")
  public void createCode(@PathVariable String type, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    validateCodeProcessors.get(type+"CodeProcessor").create(new ServletWebRequest(request, response));
  }


}

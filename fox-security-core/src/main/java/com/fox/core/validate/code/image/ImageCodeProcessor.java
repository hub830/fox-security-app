package com.fox.core.validate.code.image;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import com.fox.core.validate.code.impl.AbstractValidateCodeProcessor;

@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode>{

  @Override
  protected void send(ServletWebRequest request, ImageCode imageCode) throws IOException {
    HttpServletResponse response = request.getResponse();
    ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());    
  }

}

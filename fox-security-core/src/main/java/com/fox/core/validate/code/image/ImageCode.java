package com.fox.core.validate.code.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import com.fox.core.validate.code.ValidateCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ImageCode  extends ValidateCode{
  private BufferedImage image;

  public ImageCode(BufferedImage image, String code, int expireIn) {
    super(code, expireIn);
    this.image = image;
  }


  public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
    super(code, expireTime);
    this.image = image;
  }

}

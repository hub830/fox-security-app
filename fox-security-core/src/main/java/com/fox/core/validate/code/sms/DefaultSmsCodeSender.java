package com.fox.core.validate.code.sms;

public class DefaultSmsCodeSender implements SmsCodeSender {

  @Override
  public void send(String mobile, String code) {
    System.out.println("���ֻ�" + mobile + "���Ͷ�����֤��" + code);
  }

}

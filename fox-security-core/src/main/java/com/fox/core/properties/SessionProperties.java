package com.fox.core.properties;

import lombok.Data;

@Data
public class SessionProperties {

  /**
   * ͬһ���û���ϵͳ�е����session����Ĭ��1
   */
  private int maximumSessions = 1;
  /**
   * �ﵽ���sessionʱ�Ƿ���ֹ�µĵ�¼����Ĭ��Ϊfalse������ֹ���µĵ�¼�Ὣ�ϵĵ�¼ʧЧ��
   */
  private boolean maxSessionsPreventsLogin;
  /**
   * sessionʧЧʱ��ת�ĵ�ַ
   */
  private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;
}

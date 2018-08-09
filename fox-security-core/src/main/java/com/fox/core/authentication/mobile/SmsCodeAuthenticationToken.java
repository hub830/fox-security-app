package com.fox.core.authentication.mobile;


import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * An {@link org.springframework.security.core.Authentication} implementation that is designed for
 * simple presentation of a username and password.
 * <p>
 * The <code>principal</code> and <code>credentials</code> should be set with an <code>Object</code>
 * that provides the respective property via its <code>Object.toString()</code> method. The simplest
 * such <code>Object</code> to use is <code>String</code>.
 *
 * @author Ben Alex
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

  private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

  // ~ Instance fields
  // ================================================================================================

  private final Object principal;

  // ~ Constructors
  // ===================================================================================================

  /**
   * This constructor can be safely used by any code that wishes to create a
   * <code>UsernamePasswordAuthenticationToken</code>, as the {@link #isAuthenticated()} will return
   * <code>false</code>.
   *
   */
  public SmsCodeAuthenticationToken(String mobile) {
    super(null);
    this.principal = mobile;
    setAuthenticated(false);
  }

  /**
   * This constructor should only be used by <code>AuthenticationManager</code> or
   * <code>AuthenticationProvider</code> implementations that are satisfied with producing a trusted
   * (i.e. {@link #isAuthenticated()} = <code>true</code>) authentication token.
   *
   * @param principal
   * @param credentials
   * @param authorities
   */
  public SmsCodeAuthenticationToken(Object principal,
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    super.setAuthenticated(true); // must use super, as we override
  }

  // ~ Methods
  // ========================================================================================================


  public Object getPrincipal() {
    return this.principal;
  }

  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    if (isAuthenticated) {
      throw new IllegalArgumentException(
          "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    }

    super.setAuthenticated(false);
  }

  @Override
  public Object getCredentials() {
    return null;
  }


}

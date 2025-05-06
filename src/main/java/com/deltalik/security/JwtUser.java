package com.deltalik.security;

import com.deltalik.entity.User;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class JwtUser implements UserDetails {
  private final String email;
  private final String passwordHash;
  private final Set<SimpleGrantedAuthority> authorities;

  public JwtUser(User user) {
    this.email = user.getEmail();
    this.passwordHash = user.getPasswordHash();
    this.authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
        .collect(Collectors.toSet());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return passwordHash;
  }

  @Override
  public String getUsername() {
    return email;
  }
}

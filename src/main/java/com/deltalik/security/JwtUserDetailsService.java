package com.deltalik.security;

import com.deltalik.entity.User;
import com.deltalik.mapper.JwtMapper;
import com.deltalik.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

  private final UserService userService;
  private final JwtMapper jwtMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.getUserEntityByEmail(username);
    return jwtMapper.toJwtUser(user);
  }
}

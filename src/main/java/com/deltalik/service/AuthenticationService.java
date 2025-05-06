package com.deltalik.service;

import com.deltalik.dto.auth.JwtRequestDto;
import com.deltalik.dto.auth.JwtResponseDto;
import com.deltalik.exception.ExceptionFactory;
import com.deltalik.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  public JwtResponseDto generateToken(JwtRequestDto jwtRequestDto) {
    authenticateUser(jwtRequestDto.getEmail(), jwtRequestDto.getPassword());
    UserDetails jwtUser = userDetailsService.loadUserByUsername(jwtRequestDto.getEmail());
    return new JwtResponseDto(jwtUtil.generateToken(jwtUser));
  }

  private void authenticateUser(String username, String password) {
    try {
      UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
          username, password);
      System.out.println(authRequest);
      authenticationManager.authenticate(authRequest);
    } catch (AuthenticationException e) {
      throw ExceptionFactory.badCredentialsException();
    }
  }
}

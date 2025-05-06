package com.deltalik.controller;

import com.deltalik.dto.auth.JwtRequestDto;
import com.deltalik.dto.auth.JwtResponseDto;
import com.deltalik.dto.user.RegistrationRequestDto;
import com.deltalik.dto.user.UserResponseDto;
import com.deltalik.service.AuthenticationService;
import com.deltalik.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

  private final UserService userService;
  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody JwtRequestDto jwtRequestDto) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(authenticationService.generateToken(jwtRequestDto));
  }

  @PostMapping("/register")
  public ResponseEntity<UserResponseDto> registerUser(
      @Valid @RequestBody RegistrationRequestDto request) {
    UserResponseDto createdUser = userService.createUser(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
//        .header("Location", "/api/v1/users/" + createdUser.getId())
        .body(createdUser);
  }
}

package com.deltalik.controller;

import com.deltalik.dto.auth.JwtRequestDto;
import com.deltalik.dto.auth.JwtResponseDto;
import com.deltalik.dto.user.RegistrationRequestDto;
import com.deltalik.dto.user.UserResponseDto;
import com.deltalik.service.AuthenticationService;
import com.deltalik.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @Operation(
      summary = "Login and get JWT token",
      description = "Returns a JWT token which must be included in the Authorization header as: "
          + "`Bearer <token>` for all protected endpoints."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully authenticated",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = JwtResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "401", description = "Invalid username or password",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "405", description = "Method Not Allowed",
          content = @Content(mediaType = "application/json"))
  })
  @PostMapping("/login")
  public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody JwtRequestDto jwtRequestDto) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(authenticationService.generateToken(jwtRequestDto));
  }

  @Operation(summary = "Register a new user", description = "Create a new user account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User successfully registered",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "405", description = "Method Not Allowed",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "409", description = "User with provided email or phone number already exists",
          content = @Content(mediaType = "application/json"))
  })
  @PostMapping("/register")
  public ResponseEntity<UserResponseDto> registerUser(
      @Valid @RequestBody RegistrationRequestDto request) {
    UserResponseDto createdUser = userService.createUser(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Location", "/api/v1/users/" + createdUser.getId())
        .body(createdUser);
  }
}

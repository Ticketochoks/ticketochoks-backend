package com.deltalik.controller;

import com.deltalik.dto.user.RegistrationRequestDto;
import com.deltalik.dto.user.UserResponseDto;
import com.deltalik.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "/api/v1/users", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody RegistrationRequestDto request) {
    UserResponseDto createdUser = userService.createUser(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Location", "/api/v1/users/" + createdUser.getId())
        .body(createdUser);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUserById(
      @PathVariable @NotNull(message = "Id is required") Long id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(userService.getUserById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserById(
      @PathVariable @NotNull(message = "Id is required") Long id) {
    userService.deleteUserById(id);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }
}

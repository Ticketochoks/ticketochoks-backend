package com.deltalik.controller;

import com.deltalik.dto.user.RegistrationRequestDto;
import com.deltalik.dto.user.UserResponseDto;
import com.deltalik.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
  public ResponseEntity<UserResponseDto> createUser(
      @Valid @RequestBody RegistrationRequestDto request) {
    UserResponseDto createdUser = userService.createUser(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Location", "/api/v1/users/" + createdUser.getId())
        .body(createdUser);
  }

  @Operation(summary = "Get user by ID", description = "Retrieve user details by ID")
  @SecurityRequirement(name = "bearerAuth")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User successfully retrieved",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid ID format",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "405", description = "Method Not Allowed",
          content = @Content(mediaType = "application/json"))
  })
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUserById(
      @PathVariable @NotNull(message = "Id is required") Long id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(userService.getUserById(id));
  }

  @Operation(summary = "Delete user by ID", description = "Delete a user account by ID")
  @SecurityRequirement(name = "bearerAuth")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "User successfully deleted"),
      @ApiResponse(responseCode = "400", description = "Invalid ID format",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "403", description = "Forbidden - cannot delete this user",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "405", description = "Method Not Allowed",
          content = @Content(mediaType = "application/json"))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserById(
      @PathVariable @NotNull(message = "Id is required") Long id) {
    userService.deleteUserById(id);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }
}

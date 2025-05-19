package com.deltalik.exception;

import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;

@Slf4j
public class ExceptionFactory {

  public static ResourceNotFoundException userNotFoundById(Long id) {
    String msg = String.format("User not found by id: %d", id);
    log.warn(msg);
    return new ResourceNotFoundException(msg);
  }

  public static ResourceNotFoundException userNotFoundByEmail(String email) {
    String msg = String.format("User not found by email: %s", email);
    log.warn(msg);
    return new ResourceNotFoundException(msg);
  }

  public static DuplicateResourceException userAlreadyExists(String field, String value) {
    String msg = String.format("User with %s %s already exists", field, value);
    log.warn(msg);
    return new DuplicateResourceException(msg);
  }

  public static ResourceNotFoundException roleNotFoundByName(String name) {
    String msg = String.format("Role not found by id: %s", name);
    log.warn(msg);
    return new ResourceNotFoundException(msg);
  }

  public static BadCredentialsException badCredentialsException() {
    String msg = "Invalid username or password";
    log.warn(msg);
    return new BadCredentialsException(msg);
  }

  public static IllegalStateException authenticatedUserNotFound() {
    String msg = "Authenticated user not found";
    log.warn(msg);
    return new IllegalStateException(msg);
  }

  public static ResourceNotFoundException eventNotFoundById(Long id) {
    String msg = String.format("Event not found by id: %d", id);
    log.warn(msg);
    return new ResourceNotFoundException(msg);
  }

  public static DuplicateResourceException eventAlreadyExists(String street, String city,
      ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
    String msg = String.format(
        "An event already exists at %s street, %s city, from %s to %s. "
            + "Overlapping events are not allowed.",
        street, city, startDateTime, endDateTime);
    log.warn(msg);
    return new DuplicateResourceException(msg);
  }
}

package com.deltalik.exception;

import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionFactory {

  public static ResourceNotFoundException userNotFoundById(Long id) {
    String msg = String.format("User not found by id: %d", id);
    log.warn(msg);
    return new ResourceNotFoundException(msg);
  }

  public static DuplicateResourceException userAlreadyExists(String field, String value) {
    String msg = String.format("User with %s %s already exists", field, value);
    log.warn(msg);
    return new DuplicateResourceException(msg);
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

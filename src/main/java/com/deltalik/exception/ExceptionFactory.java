package com.deltalik.exception;

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
}

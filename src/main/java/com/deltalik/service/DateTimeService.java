package com.deltalik.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DateTimeService {

  private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");

  public ZonedDateTime parse(String dateTimeStr, Optional<String> format, Optional<String> zoneId) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
        format.orElse(DEFAULT_DATETIME_FORMAT)
    );

    ZoneId zone = zoneId.map(ZoneId::of).orElse(DEFAULT_ZONE_ID);

    try {
      LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, formatter);
      return ZonedDateTime.of(localDateTime, zone);
    } catch (DateTimeParseException e) {
      log.error("Failed to parse date/time string: {}", dateTimeStr, e);
      throw e;
    }
  }

  public String format(ZonedDateTime dateTime, Optional<String> format) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
        format.orElse(DEFAULT_DATETIME_FORMAT)
    );
    return dateTime.format(formatter);
  }
}

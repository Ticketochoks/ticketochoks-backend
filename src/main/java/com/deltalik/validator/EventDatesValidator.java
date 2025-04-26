package com.deltalik.validator;

import com.deltalik.annotation.ValidEventDates;
import com.deltalik.dto.event.EventRequestDto;
import com.deltalik.service.DateTimeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventDatesValidator implements ConstraintValidator<ValidEventDates, EventRequestDto> {

  private final DateTimeService dateTimeService;

  @Override
  public boolean isValid(EventRequestDto dto, ConstraintValidatorContext context) {
    if (dto.getStartDateTime() == null || dto.getStartDateTime().isEmpty()
        || dto.getEndDateTime() == null || dto.getEndDateTime().isEmpty()) {
      buildConstraintViolation(context, "Start and end date/time must not be blank");
      return false;
    }

    ZonedDateTime start, end;
    try {
      start = dateTimeService.parse(
          dto.getStartDateTime(),
          Optional.ofNullable(dto.getDateTimeFormat()),
          Optional.ofNullable(dto.getTimezone())
      );
    } catch (DateTimeParseException e) {
      buildConstraintViolation(context, "Invalid start date/time format");
      return false;
    } catch (Exception e) {
      buildConstraintViolation(context, "Invalid timezone or date format for start date/time");
      return false;
    }

    try {
      end = dateTimeService.parse(
          dto.getEndDateTime(),
          Optional.ofNullable(dto.getDateTimeFormat()),
          Optional.ofNullable(dto.getTimezone())
      );
    } catch (DateTimeParseException e) {
      buildConstraintViolation(context, "Invalid end date/time format");
      return false;
    } catch (Exception e) {
      buildConstraintViolation(context, "Invalid timezone or date format for end date/time");
      return false;
    }

    if (!start.isBefore(end)) {
      buildConstraintViolation(context, "Start date/time must be before end date/time");
      return false;
    }
    return true;
  }

  private void buildConstraintViolation(ConstraintValidatorContext context, String message) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(message)
        .addConstraintViolation();
  }
}

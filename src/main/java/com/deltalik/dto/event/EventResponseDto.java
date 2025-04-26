package com.deltalik.dto.event;

import com.deltalik.dto.common.BaseResponse;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class EventResponseDto extends BaseResponse {

  private Long id;
  private String name;
  private EventLocationResponseDto location;
  private ZonedDateTime startDateTime;
  private ZonedDateTime endDateTime;
  private String timezone;
  private int availableTickets;
  private int ticketPrice;
}

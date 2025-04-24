package com.deltalik.dto.event;

import com.deltalik.dto.common.BaseResponse;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventResponseDto extends BaseResponse {

  private Long id;
  private String name;
  private EventLocationDto location;
  private ZonedDateTime startDateTime;
  private ZonedDateTime endDateTime;
  private int availableTickets;
  private int ticketPrice;
}

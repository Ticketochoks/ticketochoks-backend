package com.deltalik.dto.ticket;

import com.deltalik.dto.common.BaseResponse;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class TicketResponseDto extends BaseResponse {

  private Long id;
  private Long userId;
  private Long eventId;
  private ZonedDateTime purchaseDateTime;
  private SeatDto seatDto;
  private Integer pricePaid; // TODO: move to BigDecimal
  private String status;
}

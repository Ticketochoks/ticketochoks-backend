package com.deltalik.dto.common;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseResponse {
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String createdBy;
  private String updatedBy;
}

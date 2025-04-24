package com.deltalik.dto.user;

import com.deltalik.dto.common.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserResponseDto extends BaseResponse {

  private Long id;
  private String email;
  private String phoneNumber;
  private String firstName;
  private String lastName;
}

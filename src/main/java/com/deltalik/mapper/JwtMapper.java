package com.deltalik.mapper;

import com.deltalik.entity.User;
import com.deltalik.security.JwtUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface JwtMapper {
  JwtUser toJwtUser(User user);
}

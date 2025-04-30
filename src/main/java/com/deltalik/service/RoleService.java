package com.deltalik.service;

import com.deltalik.entity.Role;
import com.deltalik.entity.RoleType;
import com.deltalik.exception.ExceptionFactory;
import com.deltalik.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService {

  private final RoleRepository roleRepository;

  public Role getUserRole() {
    String name = RoleType.USER.name();
    return roleRepository.findByName(name).orElseThrow(
        () -> ExceptionFactory.roleNotFoundByName(name)
    );
  }

  public Role getOrganizerRole() {
    String name = RoleType.ORGANIZER.name();
    return roleRepository.findByName(name).orElseThrow(
        () -> ExceptionFactory.roleNotFoundByName(name)
    );
  }

  public Role getModeratorRole() {
    String name = RoleType.MODERATOR.name();
    return roleRepository.findByName(name).orElseThrow(
        () -> ExceptionFactory.roleNotFoundByName(name)
    );
  }

  public Role getAdminRole() {
    String name = RoleType.ADMIN.name();
    return roleRepository.findByName(name).orElseThrow(
        () -> ExceptionFactory.roleNotFoundByName(name)
    );
  }
}

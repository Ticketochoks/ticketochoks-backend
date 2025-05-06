package com.deltalik.config;

import com.deltalik.entity.Role;
import com.deltalik.entity.RoleType;
import com.deltalik.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleInitializer {

  private final RoleRepository roleRepository;

  @PostConstruct
  public void init() {
    Set<String> existingRoles = roleRepository.findAll()
        .stream()
        .map(Role::getName)
        .collect(Collectors.toSet());

    List<Role> missingRoles = Arrays.stream(RoleType.values())
        .map(Enum::name)
        .filter(roleName -> !existingRoles.contains(roleName))
        .map(roleName -> Role.builder().name(roleName).build())
        .toList();

    if (!missingRoles.isEmpty()) {
      roleRepository.saveAll(missingRoles);
    }
  }
}

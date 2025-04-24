package com.deltalik.service;

import com.deltalik.dto.user.UserRequestDto;
import com.deltalik.dto.user.UserResponseDto;
import com.deltalik.entity.Role;
import com.deltalik.entity.User;
import com.deltalik.exception.ExceptionFactory;
import com.deltalik.mapper.UserMapper;
import com.deltalik.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Transactional
  public UserResponseDto createUser(UserRequestDto registrationRequestDto) {
    validateUserDoesNotExist(registrationRequestDto.getEmail(),
        registrationRequestDto.getPhoneNumber());

    User user = User.builder()
        .email(registrationRequestDto.getEmail())
        .firstName(registrationRequestDto.getFirstName())
        .lastName(registrationRequestDto.getLastName())
        .phoneNumber(registrationRequestDto.getPhoneNumber())
        .passwordHash(registrationRequestDto.getPassword())
        .role(Role.USER)
        .build();

    User savedUser = userRepository.save(user);
    return userMapper.toUserResponseDto(savedUser);
  }

  public UserResponseDto getUserById(Long id) {
    User user = userRepository.findById(id).orElseThrow(
        () -> ExceptionFactory.userNotFoundById(id));

    log.info("User is found by id {}", id);
    return userMapper.toUserResponseDto(user);
  }

  @Transactional
  public void deleteUserById(Long id) {
    userRepository.findById(id).orElseThrow(
        () -> ExceptionFactory.userNotFoundById(id));

    userRepository.deleteById(id);
    log.info("User is deleted by id {}", id);
  }

  private void validateUserDoesNotExist(String email, String phoneNumber) {
    userRepository.findByEmailOrPhoneNumber(email, phoneNumber).ifPresent(
        user -> {
          if (user.getEmail().equals(email)) {
            throw ExceptionFactory.userAlreadyExists("email", email);
          } else {
            throw ExceptionFactory.userAlreadyExists("phone number", phoneNumber);
          }
        }
    );
  }
}

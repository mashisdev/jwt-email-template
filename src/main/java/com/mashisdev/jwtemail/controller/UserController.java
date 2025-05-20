package com.mashisdev.jwtemail.controller;

import com.mashisdev.jwtemail.dto.response.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserController {

    ResponseEntity<UserDto> findById(Long id);

    ResponseEntity<List<UserDto>> findAll();

    ResponseEntity<UserDto> update(UserDto userDto);

    ResponseEntity<Void> delete(Long id);
}
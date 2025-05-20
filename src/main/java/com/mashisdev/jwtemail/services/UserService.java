package com.mashisdev.jwtemail.services;

import com.mashisdev.jwtemail.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    User findById(UUID id);

    List<User> findAll();

    User update(User user);

    void delete(UUID id);

}

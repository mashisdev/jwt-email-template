package com.mashisdev.jwtemail.services;

import com.mashisdev.jwtemail.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}

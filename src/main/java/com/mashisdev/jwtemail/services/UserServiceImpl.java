package com.mashisdev.jwtemail.services;

import com.mashisdev.jwtemail.exception.auth.user.UserNotFoundException;
import com.mashisdev.jwtemail.model.User;
import com.mashisdev.jwtemail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void delete(UUID id) { userRepository.deleteById(id); }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User updateUser) {
        User user = userRepository.findById(updateUser.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        BeanUtils.copyProperties(updateUser, user, "role");
        return userRepository.save(user);
    }

}

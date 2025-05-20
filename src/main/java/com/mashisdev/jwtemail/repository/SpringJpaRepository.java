package com.mashisdev.jwtemail.repository;

import com.mashisdev.jwtemail.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringJpaRepository extends JpaRepository<UserEntity,UUID> {
    Boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}

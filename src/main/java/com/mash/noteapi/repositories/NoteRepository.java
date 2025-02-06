package com.mash.noteapi.repositories;

import com.mash.noteapi.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserId(Long userId);

    Optional<Note> findByIdAndUserId(Long id, Long userId);

    List<Note> findByUserIdAndCategory(Long userId, String category);

    List<Note> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

//    @Query("SELECT n FROM Note n WHERE n.user.id = :userId AND LOWER(n.content) LIKE LOWER(CONCAT('%', :text, '%'))")
//    List<Note> searchByContent(@Param("userId") Long userId, @Param("text") String text);

    List<Note> findByUserIdAndContentContainingIgnoreCase(Long userId, String keyword);
}

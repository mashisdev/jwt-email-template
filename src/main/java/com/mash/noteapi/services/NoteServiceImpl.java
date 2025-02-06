package com.mash.noteapi.services;

import com.mash.noteapi.entities.Note;
import com.mash.noteapi.entities.User;
import com.mash.noteapi.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Override
    public List<Note> getAllNotes() {
        return List.of();
    }

    @Override
    public Optional<Note> getNoteById(Long noteId) {
        return Optional.empty();
    }

    @Override
    public Note createNote(Note note) {
        return null;
    }

    @Override
    public Optional<Note> updateNote(Long noteId, Note noteDetails) {
        return Optional.empty();
    }

    @Override
    public boolean deleteNote(Long noteId) {
        return false;
    }
}

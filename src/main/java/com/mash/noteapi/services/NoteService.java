package com.mash.noteapi.services;

import com.mash.noteapi.entities.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {

        List<Note> getAllNotes();

        Optional<Note> getNoteById(Long noteId);

        Note createNote(Note note);

        Optional<Note> updateNote(Long noteId, Note noteDetails);

        boolean deleteNote(Long noteId);
}

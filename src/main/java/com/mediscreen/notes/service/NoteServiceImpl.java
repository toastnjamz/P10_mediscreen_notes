package com.mediscreen.notes.service;

import com.mediscreen.notes.domain.Note;
import com.mediscreen.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> findAllNotesByPatientId(int patientId) {
        return noteRepository.findNotesByPatientId(patientId);
    }

    @Override
    public Note findById(String id) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        if (noteOptional.isPresent()) {
            return noteOptional.get();
        }
        return null;
    }

    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public void updateNote(Note note) {
        Optional<Note> noteOptional = noteRepository.findById(note.getId());
        if (noteOptional.isPresent()) {
            noteRepository.save(note);
        }
    }

    @Override
    public void deleteNote(String noteId) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        if (noteOptional.isPresent()) {
            noteRepository.delete(noteOptional.get());
        }
    }

}

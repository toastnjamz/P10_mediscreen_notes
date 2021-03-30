package com.mediscreen.notes.service.note;

import com.mediscreen.notes.domain.note.Note;
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

    /**
     * Gets a list of all notes in the repository for the specified patientId
     * @param patientId
     * @return List<Note>
     */
    @Override
    public List<Note> findAllNotesByPatientId(int patientId) {
        return noteRepository.findNotesByPatientId(patientId);
    }

    /**
     * Gets a note by id
     * @param id
     * @return Note
     */
    @Override
    public Note findById(String id) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        if (noteOptional.isPresent()) {
            return noteOptional.get();
        }
        return null;
    }

    /**
     * Saves a note to the repository
     * @param note
     * @return Note
     */
    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    /**
     * Updates an existing note in the repository
     * @param note
     */
    @Override
    public void updateNote(Note note) {
        Optional<Note> noteOptional = noteRepository.findById(note.getId());
        if (noteOptional.isPresent()) {
            noteRepository.save(note);
        }
    }

    /**
     * Deletes an existing note from the repository
     * @param noteId
     */
    @Override
    public void deleteNote(String noteId) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        if (noteOptional.isPresent()) {
            noteRepository.delete(noteOptional.get());
        }
    }

    @Override
    public void deleteAllNotes() {
        noteRepository.deleteAll();
    }

}

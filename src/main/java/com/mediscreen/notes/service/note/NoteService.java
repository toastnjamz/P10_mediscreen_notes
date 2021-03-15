package com.mediscreen.notes.service.note;

import com.mediscreen.notes.domain.note.Note;

import java.util.List;

public interface NoteService {

    public List<Note> findAllNotesByPatientId(int patientId);

    public Note findById(String id);

    public Note createNote(Note note);

    public void updateNote(Note note);

    public void deleteNote(String noteId);

}

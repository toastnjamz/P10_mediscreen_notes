package com.mediscreen.notes.repository;

import com.mediscreen.notes.domain.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, Integer> {

    List<Note> findNotesByPatientId(int id);
}

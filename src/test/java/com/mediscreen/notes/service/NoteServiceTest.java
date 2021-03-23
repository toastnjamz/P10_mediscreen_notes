package com.mediscreen.notes.service;

import com.mediscreen.notes.domain.note.Note;
import com.mediscreen.notes.domain.patient.Patient;
import com.mediscreen.notes.repository.NoteRepository;
import com.mediscreen.notes.service.note.NoteServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepositoryMock;

    @InjectMocks
    private NoteServiceImpl noteService;

    private Patient patient;
    private Note note;

    @BeforeAll
    public void setup() {
        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
        Patient patient = new Patient();
        patient.setId(100);
        patient.setGivenName("TestGiven");
        patient.setFamilyName("TestFamily");
        patient.setDob(dob);
        patient.setSex('F');

        Note note = new Note();
        note.setId("123");
        note.setNote("Test note");
        note.setPatientId(100);
    }

    @Test
    public void findAllNotesByPatientId_patientIdExists_notesReturned() {
        // arrange
        List<Note> notes = new ArrayList<>();
        notes.add(note);

        when(noteRepositoryMock.findNotesByPatientId(100)).thenReturn(notes);

        // act
        List<Note> listResult = noteService.findAllNotesByPatientId(100);

        // assert
        assertTrue(listResult.size() > 0);
    }

    @Test
    public void findAllNotesByPatientId_patientIdDoesNotExist_nullReturned() {
        // arrange

        // act
        List<Note> listResult = noteService.findAllNotesByPatientId(100);

        // assert
        assertTrue(listResult.size() == 0);
    }

    @Test
    public void findAById_noteExists_noteReturned() {
        // arrange

        when(noteRepositoryMock.findById("123")).thenReturn(java.util.Optional.ofNullable(note));

        // act
        Note result = noteService.findById("123");

        // assert
        assertEquals(note, result);
    }

    @Test
    public void findById_noteDoesNotExist_nullReturned() {
        // arrange

        // act
        Note result = noteService.findById("456");

        // assert
        assertNull(result);
    }

    @Test
    public void createNote_noteValid_noteSaved() {
        // arrange
        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
        Patient patient1 = new Patient();
        patient1.setId(101);
        patient1.setGivenName("TestGiven");
        patient1.setFamilyName("TestFamily");
        patient1.setDob(dob);
        patient1.setSex('F');

        Note note1 = new Note();
        note1.setId("456");
        note1.setNote("Test note");
        note1.setPatientId(101);

        when(noteRepositoryMock.save(note1)).thenReturn(note1);

        // act
        Note result = noteService.createNote(note1);

        // assert
        verify(noteRepositoryMock, times(1)).save(any(Note.class));
        assertEquals(note1.getId(), result.getId());
    }

    @Test
    public void updateNote_noteValid_noteUpdated() {
        // arrange
        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
        Patient patient1 = new Patient();
        patient1.setId(101);
        patient1.setGivenName("TestGiven");
        patient1.setFamilyName("TestFamily");
        patient1.setDob(dob);
        patient1.setSex('F');

        Note note1 = new Note();
        note1.setId("456");
        note1.setNote("Test note");
        note1.setPatientId(101);

        noteService.createNote(note1);
        note1.setNote("Updated note");

        // act
        noteService.updateNote(note1);

        // assert
        verify(noteRepositoryMock, times(1)).save(any(Note.class));
        assertEquals(note1.getNote(), "Updated note");
    }

    @Test
    public void deleteNote_noteValid_noteDeleted() {
        // arrange

        // act
        noteService.deleteNote("456");

        // assert
        Optional<Note> note = noteRepositoryMock.findById("456");
        assertFalse(note.isPresent());
    }

}

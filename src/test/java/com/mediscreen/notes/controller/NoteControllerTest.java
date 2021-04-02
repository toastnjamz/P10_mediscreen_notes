package com.mediscreen.notes.controller;

import com.mediscreen.notes.domain.note.Note;
import com.mediscreen.notes.domain.patient.Patient;
import com.mediscreen.notes.service.note.NoteServiceImpl;
import com.mediscreen.notes.service.patient.PatientServiceClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class NoteControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    private NoteServiceImpl noteServiceMock;

    @MockBean
    private PatientServiceClient patientServiceClientMock;

    private Note note;
    private Patient patient;

    @BeforeAll
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();

        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
        patient = new Patient();
        patient.setId(100);
        patient.setGivenName("TestGiven");
        patient.setFamilyName("TestFamily");
        patient.setDob(dob);
        patient.setSex('F');

        note = new Note();
        note.setId("123");
        note.setNote("Test note");
        note.setPatientId(100);
    }

    @Test
    public void getNoteList_statusIsSuccessful() throws Exception {
        mockMvc.perform(get("/noteList/" + 100))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void showAllNotesByPatientId_statusIsSuccessful() throws Exception {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        List<Note> noteList = new ArrayList<>();
        noteList.add(note);

        when(patientServiceClientMock.findPatientInList(100)).thenReturn(patient);

        mockMvc.perform(get("/note/list/{patientId}","100"))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void addNote_statusIsSuccessful() throws Exception {
        when(patientServiceClientMock.findPatientInList(100)).thenReturn(patient);

        mockMvc.perform(get("/note/add/{patientId}", "100"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void validate_statusIsSuccessful() throws Exception {
        mockMvc.perform(post("/note/validate/{patientId}", "100")
                .param("note", "Test note"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void showUpdateForm_statusIsSuccessful() throws Exception {
        when(patientServiceClientMock.findPatientInList(100)).thenReturn(patient);
        when(noteServiceMock.findById("123")).thenReturn(note);

        mockMvc.perform(get("/note/update/{id}", "123"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void updateNote_statusIsSuccessful() throws Exception {
        List<Note> noteList = new ArrayList<>();
        noteList.add(note);

        when(patientServiceClientMock.findPatientInList(100)).thenReturn(patient);
        when(noteServiceMock.findAllNotesByPatientId(100)).thenReturn(noteList);
        when(noteServiceMock.findById("123")).thenReturn(note);

        mockMvc.perform(post("/note/update/{id}", "123"))
//                .param("note", "Updated note"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deleteNote_statusIsRedirection() throws Exception {
        when(patientServiceClientMock.findPatientInList(101)).thenReturn(patient);
        when(noteServiceMock.findById("456")).thenReturn(note);

        mockMvc.perform(get("/note/delete/{id}", "456"))
                .andExpect(status().is3xxRedirection());
    }

}

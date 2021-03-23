package com.mediscreen.notes.controller;

import com.mediscreen.notes.domain.note.Note;
import com.mediscreen.notes.domain.patient.Patient;
import com.mediscreen.notes.service.note.NoteServiceImpl;
import com.mediscreen.notes.service.patient.PatientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyInt;
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
    private PatientService patientServiceMock;

    @InjectMocks
    private NoteServiceImpl noteService;

    private Note note;
    private Patient patient;

    @BeforeAll
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();

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
    public void showAllNotesByPatientId_statusIsSuccessful() throws Exception {
//        List<Patient> patientList = new ArrayList<>();
//        patientList.add(patient);

        List<Note> noteList = new ArrayList<>();
        noteList.add(note);

        when(patientServiceMock.findPatientInList(anyInt())).thenReturn(patient);
        when(noteServiceMock.findAllNotesByPatientId(100)).thenReturn(noteList);

//        mockMvc.perform(get("/note/list/{patientId}", "100"))
        mockMvc.perform(get("/note/list/" + 100))
//                .param("patientId", "100"))
                .andExpect(status().is2xxSuccessful());
//                .andExpect(view().name("note/list"));

    }


}

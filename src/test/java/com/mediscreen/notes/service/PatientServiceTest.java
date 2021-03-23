//package com.mediscreen.notes.service;
//
//import com.mediscreen.notes.domain.patient.Patient;
//import com.mediscreen.notes.service.patient.PatientService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.client.RestTemplate;
//
//import static org.junit.Assert.*;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class PatientServiceTest {
//
//    private RestTemplate restTemplate;
//    private PatientService patientService;
//    private Patient patient;
//
//    @BeforeAll
//    public void setup() {
//        restTemplate = new RestTemplate();
//
//
//        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
//        Patient patient = new Patient();
//        patient.setId(100);
//        patient.setGivenName("TestGiven");
//        patient.setFamilyName("TestFamily");
//        patient.setDob(dob);
//        patient.setSex('F');
//        patient.setAddress("123 Test Street");
//        patient.setPhone("555-555-5555");
//    }
//
//    @Test
//    public void getPatientsList_patientsExist_patientsListReturned() {
//        // arrange
//        List<Patient> patients = new ArrayList<>();
//        patients.add(patient);
//        when statement
//
//        // act
//        List<Patient> listResult = patientService.getPatientsList();
//
//        // assert
//        assertTrue(listResult.size() > 0);
//    }
//
//
//
//
//}

//package com.mediscreen.notes.service;
//
//import com.mediscreen.notes.domain.patient.Patient;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDate;
//
//@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class PatientServiceTest {
//
//    private RestTemplate restTemplate;
//    private Patient patient;
//
//    @BeforeAll
//    public void setup() {
//        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
//        Patient patient = new Patient();
//        patient.setId(100);
//        patient.setGivenName("TestGiven");
//        patient.setFamilyName("TestFamily");
//        patient.setDob(dob);
//        patient.setSex('F');
//        patient.setAddress("123 Test Street");
//        patient.setPhone("555-555-5555");
//
//    }
//
//
//}

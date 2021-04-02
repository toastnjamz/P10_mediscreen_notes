package com.mediscreen.notes.service;

import com.mediscreen.notes.domain.patient.Patient;
import com.mediscreen.notes.service.patient.PatientServiceClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PatientServiceClientTest {

    private PatientServiceClient patientServiceClient;
    private RestTemplate restTemplate;
    private Patient patient;
    private List<Patient> patients;

    @BeforeAll
    public void setup() {
        restTemplate = new RestTemplate();
        patientServiceClient = new PatientServiceClient(restTemplate);

        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
        patient = new Patient();
        patient.setId(100);
        patient.setGivenName("TestGiven");
        patient.setFamilyName("TestFamily");
        patient.setDob(dob);
        patient.setSex('F');
        patient.setAddress("123 Test Street");
        patient.setPhone("555-555-5555");

        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
    }

    @Test
    public void getPatientsList_patientsExist_patientsListReturned() {
        // arrange

        // act
        List<Patient> listResult = patientServiceClient.getPatientsList();

        // assert
        assertTrue(listResult.size() > 0);
    }

    @Test
    public void findPatientInList_patientsExist_patientReturned() {
        // arrange
        String address = "1202 Bumble Dr";

        // act
        Patient result = patientServiceClient.findPatientInList(10);

        // assert
        assertEquals(address, result.getAddress());
    }

    @Test
    public void findPatientInList_patientDoesNotExist_nullReturned() {
        // arrange

        // act
        Patient result = patientServiceClient.findPatientInList(100);

        // assert
        assertNull(result);
    }

}

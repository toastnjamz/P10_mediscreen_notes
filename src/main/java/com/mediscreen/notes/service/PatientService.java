package com.mediscreen.notes.service;

import com.mediscreen.notes.domain.Patient;
import com.mediscreen.notes.domain.PatientListWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PatientService {

    private final RestTemplate restTemplate;

    private final String requestURI = "http://localhost:8081//patientsList";

    public PatientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Patient> getPatientsList() {
        List<Patient> patientsList;
        PatientListWrapper patientListWrapper = restTemplate.getForObject(requestURI, PatientListWrapper.class);
        patientsList = patientListWrapper.getPatientList();
        return patientsList;
    }

    public Patient findPatientInList(int patientId) {
        List<Patient> patientsList = getPatientsList();
        for (Patient patient : patientsList) {
            if (patient.getId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }
}

package com.mediscreen.notes.service.patient;

import com.mediscreen.notes.domain.patient.Patient;
import com.mediscreen.notes.domain.patient.PatientListWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PatientServiceClient {

    private final RestTemplate restTemplate;

    private final String requestURI = "http://localhost:8081//patientsList";

    public PatientServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Gets a list of all patients from the Patients microservice
     * @return List<Patient>
     */
    public List<Patient> getPatientsList() {
        List<Patient> patientsList;
        PatientListWrapper patientListWrapper = restTemplate.getForObject(requestURI, PatientListWrapper.class);
        patientsList = patientListWrapper.getPatientList();
        return patientsList;
    }

    /**
     * Checks if a patient is in the list of existing patients by patientId
     * @param patientId
     * @return Patient
     */
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

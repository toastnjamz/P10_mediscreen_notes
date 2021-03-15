package com.mediscreen.notes.domain;

import java.util.List;

public class PatientListWrapper {

    private List<Patient> patientList;

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }
}

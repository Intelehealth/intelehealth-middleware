
package com.emrmiddleware.dto;

import com.emrmiddleware.dto.PatientDTO;
import java.util.ArrayList;

public class PushBodyDTO {
    private ArrayList<PatientDTO> patientList;

    public ArrayList<PatientDTO> getPatientList() {
        return this.patientList;
    }

    public void setPatientList(ArrayList<PatientDTO> patientList) {
        this.patientList = patientList;
    }
}


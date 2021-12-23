package com.company.clinic.visit;

import com.company.clinic.dto.VisitDto;
import com.company.clinic.model.doctor.Doctor;
import com.company.clinic.model.patient.Patient;
import com.company.clinic.model.visit.Visit;
import com.company.clinic.model.visit.VisitStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

public class VisitToDtoTest {
    @Test
    public void test() {

        Doctor doctor = new Doctor();
        doctor.setFirstName("Dolityl");

        Patient patient = new Patient();
        patient.setNameOfAnimal("Burek");

        Visit visit = new Visit();
        visit.setStatus(VisitStatus.CREATED);
        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visit.setVisitTime(LocalDateTime.now());

        VisitDto map = new ModelMapper().map(visit, VisitDto.class);
        Assertions.assertEquals("Dolityl", map.getDoctor().getFirstName());
        Assertions.assertEquals("Burek", map.getPatient().getNameOfAnimal());
    }
}

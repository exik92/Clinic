package com.company.clinic.visit;

import com.company.clinic.command.CreateDoctorCommand;
import com.company.clinic.command.CreatePatientCommand;
import com.company.clinic.command.CreateVisitCommand;
import com.company.clinic.command.VisitActionCommand;
import com.company.clinic.common.IntegrationTest;
import com.company.clinic.dto.DoctorDto;
import com.company.clinic.dto.PatientDto;
import com.company.clinic.dto.VisitDto;
import com.company.clinic.exception.EntityNotFoundException;
import com.company.clinic.model.doctor.MedicalAnimalSpecialization;
import com.company.clinic.model.doctor.MedicalSpecialization;
import com.company.clinic.model.patient.AnimalRace;
import com.company.clinic.model.patient.AnimalType;
import com.company.clinic.model.visit.VisitAction;
import com.company.clinic.model.visit.VisitStatus;
import com.company.clinic.model.visit.VisitToken;
import com.company.clinic.repository.VisitTokenRepository;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VisitIntegrationTests extends IntegrationTest {

    @Autowired
    private VisitTokenRepository visitTokenRepository;

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void createVisit() throws Exception {
        //given
        CreatePatientCommand createPatientCommand = getPatientDto();
        PatientDto patientDto = addPatient(createPatientCommand, jwtToken);

        CreateDoctorCommand createDoctorCommand = getDoctorDto();
        DoctorDto doctorDto = addDoctor(createDoctorCommand, jwtToken);

        CreateVisitCommand createVisitCommand = new CreateVisitCommand(doctorDto.getId(), patientDto.getId(), LocalDateTime.now());

        //when
        mvc.perform(MockMvcRequestBuilders
                        .post("/visits")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(createVisitCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isCreated());
    }

    @Test
    public void throwExceptionBecauseDoctorNotExistsWhileCreatingVisit() throws Exception {
        //given
        CreatePatientCommand createPatientCommand = getPatientDto();
        PatientDto patientDto = addPatient(createPatientCommand, jwtToken);

        long doctorId = 23;
        CreateVisitCommand createVisitCommand = new CreateVisitCommand(doctorId, patientDto.getId(), LocalDateTime.now());

        //when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/visits")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(createVisitCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        //then
        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue
                (content.contains(
                        String.format("Doctor with id: %s does not exist", doctorId)));
    }

    @Test
    public void throwExceptionBecausePatientNotExistsWhileCreatingVisit() throws Exception {
        //given
        CreateDoctorCommand createDoctorCommand = getDoctorDto();
        DoctorDto doctorDto = addDoctor(createDoctorCommand, jwtToken);

        long patientId = 12;
        CreateVisitCommand createVisitCommand = new CreateVisitCommand(doctorDto.getId(), patientId, LocalDateTime.now());

        //when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/visits")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(createVisitCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        //then
        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue
                (content.contains(
                        String.format("Patient with id: %s does not exist", patientId)));
    }

    @Test
    public void confirmVisit() throws Exception {
        //given
        CreateDoctorCommand createDoctorCommand = getDoctorDto();
        DoctorDto doctorDto = addDoctor(createDoctorCommand, jwtToken);

        CreatePatientCommand createPatientCommand = getPatientDto();
        PatientDto patientDto = addPatient(createPatientCommand, jwtToken);

        CreateVisitCommand createVisitCommand = new CreateVisitCommand(doctorDto.getId(), patientDto.getId(), LocalDateTime.now());
        VisitDto visitDto = addVisit(createVisitCommand, jwtToken);
        VisitActionCommand visitActionCommand = new VisitActionCommand(visitDto.getId(), VisitAction.CONFIRM);


        //when
        mvc.perform(MockMvcRequestBuilders
                        .put("/visits/confirm")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(visitActionCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/visits/" + visitDto.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        assertThat(result.getResponse().getContentAsString()).contains(VisitStatus.CONFIRMED.toString());
    }


    @Test
    public void shouldReturnErrorWhenPatientIsBusy() throws Exception {

        // given
        DoctorDto doctorDto0 = addDoctor(new CreateDoctorCommand(
                "John", "Doe",
                MedicalSpecialization.DERMATOLOGY,
                MedicalAnimalSpecialization.BIRD,
                100,
                100123332), jwtToken);

        DoctorDto doctorDto1 = addDoctor(new CreateDoctorCommand(
                "Janusz", "Nosacz",
                MedicalSpecialization.EYE_DOCTOR,
                MedicalAnimalSpecialization.DOG,
                100,
                666123000), jwtToken);

        CreatePatientCommand createPatientCommand = getPatientDto();
        PatientDto patientDto = addPatient(createPatientCommand, jwtToken);

        CreateVisitCommand createVisitCommand = new CreateVisitCommand(
                doctorDto0.getId(),
                patientDto.getId(),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 15)));
        addVisit(createVisitCommand, jwtToken);

        // when
        CreateVisitCommand secondVisit = new CreateVisitCommand(
                doctorDto1.getId(),
                patientDto.getId(),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30)));

        ResultActions perform = mvc.perform(MockMvcRequestBuilders
                .post("/visits")
                .header("Authorization", "Bearer " + jwtToken)
                .content(objectMapper.writeValueAsString(secondVisit))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        // then
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        Assertions.assertTrue
                (contentAsString.contains(
                        String.format("Patient with id: %s has another visit on this time", patientDto.getId())));
    }


    @Test
    public void shouldReturnErrorWhenDoctorIsBusy() throws Exception {
        // given
        CreateDoctorCommand createDoctorCommand = getDoctorDto();
        DoctorDto doctorDto = addDoctor(createDoctorCommand, jwtToken);

        CreatePatientCommand createPatientCommand = getPatientDto();
        PatientDto patientDto = addPatient(createPatientCommand, jwtToken);

        CreateVisitCommand createVisitCommand = new CreateVisitCommand(
                doctorDto.getId(),
                patientDto.getId(),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 15)));
        addVisit(createVisitCommand, jwtToken);

        // when
        CreateVisitCommand secondVisit = new CreateVisitCommand(
                doctorDto.getId(),
                patientDto.getId(),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30)));

        ResultActions perform = mvc.perform(MockMvcRequestBuilders
                .post("/visits")
                .header("Authorization", "Bearer " + jwtToken)
                .content(objectMapper.writeValueAsString(secondVisit))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        Assertions.assertTrue(
                contentAsString.contains(
                        String.format("Doctor with id: %s has another visit on this time", doctorDto.getId())));

    }

    @Test
    public void cancelVisit() throws Exception {
        //given
        CreateDoctorCommand createDoctorCommand = getDoctorDto();
        DoctorDto doctorDto = addDoctor(createDoctorCommand, jwtToken);

        CreatePatientCommand createPatientCommand = getPatientDto();
        PatientDto patientDto = addPatient(createPatientCommand, jwtToken);

        CreateVisitCommand createVisitCommand = new CreateVisitCommand(doctorDto.getId(), patientDto.getId(), LocalDateTime.now());
        VisitDto dto = addVisit(createVisitCommand, jwtToken);
        VisitActionCommand visitActionCommand = new VisitActionCommand(dto.getId(), VisitAction.CONFIRM);

        //when
        mvc.perform(MockMvcRequestBuilders
                        .put("/visits/cancel")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(visitActionCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/visits/" + dto.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        assertThat(result.getResponse().getContentAsString()).contains(VisitStatus.CANCELLED.toString());
    }


    private CreateDoctorCommand getDoctorDto() {
        return new CreateDoctorCommand(
                "John", "Doe", MedicalSpecialization.DERMATOLOGY, MedicalAnimalSpecialization.BIRD, 100, 100123332
        );
    }

    private CreatePatientCommand getPatientDto() {
        return new CreatePatientCommand(
                "Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 1, "John", "John@email.com"
        );
    }

    private String getTokenForGivenId(long visitId) {
        VisitToken visitToken = visitTokenRepository.findById(visitId)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));
        return visitToken.getToken();
    }
}


package com.company.clinic.patient;

import com.company.clinic.command.CreatePatientCommand;
import com.company.clinic.common.IntegrationTest;
import com.company.clinic.dto.PatientDto;
import com.company.clinic.model.patient.AnimalRace;
import com.company.clinic.model.patient.AnimalType;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientIntegrationTests extends IntegrationTest {

    @Test
    public void getAllPatients() throws Exception {
        //when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/patients")
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andReturn();

        //then
        String content = result.getResponse().getContentAsString();
        List<PatientDto> patients = objectMapper.readValue(content, new TypeReference<List<PatientDto>>() {
        });
        assertThat(patients).hasSize(0);

    }

    @Test
    public void getPatientById() throws Exception {
        //given
        CreatePatientCommand createPatientCommand = getPatientDto();
        PatientDto patientDto = addPatient(createPatientCommand, jwtToken);

        //when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/patients/" + patientDto.getId())
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String content = result.getResponse().getContentAsString();
        PatientDto patient = objectMapper.readValue(content, PatientDto.class);
        assertThat(patient.getEmail()).isEqualTo(createPatientCommand.getEmail());
    }

    @Test
    public void getListOfPatients() throws Exception {
        //given
        CreatePatientCommand createPatientCommand = getPatientDto();
        addPatient(createPatientCommand, jwtToken);

        //when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/patients")
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String content = result.getResponse().getContentAsString();
        List<PatientDto> patients = objectMapper.readValue(content, new TypeReference<List<PatientDto>>() {
        });
        assertThat(patients.get(0).getEmail()).isEqualTo(createPatientCommand.getEmail());
    }

    @Test
    public void returnBadRequestWhenPatientAlreadyExist() throws Exception {
        CreatePatientCommand createPatientCommand = getPatientDto();
        addPatient(createPatientCommand, jwtToken);

        // try again
        mvc.perform(MockMvcRequestBuilders
                        .post("/patients")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(createPatientCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private CreatePatientCommand getPatientDto() {
        return new CreatePatientCommand(
                "Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 1, "John", "John@email.com"
        );
    }
}

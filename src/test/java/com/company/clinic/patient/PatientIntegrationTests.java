package com.company.clinic.patient;

import com.company.clinic.command.CreatePatientCommand;
import com.company.clinic.common.IntegrationTest;
import com.company.clinic.dto.PatientDto;
import com.company.clinic.model.patient.AnimalRace;
import com.company.clinic.model.patient.AnimalType;
import com.company.clinic.util.TypeUtils;
import com.fasterxml.jackson.databind.JavaType;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        JavaType type = objectMapper.getTypeFactory().constructParametricType(TypeUtils.TestPageResult.class, PatientDto.class);
        TypeUtils.TestPageResult<PatientDto> response = objectMapper.readValue(content, type);

        assertThat(response.getContent()).hasSize(0);
    }

    @Test
    public void getListOfPatients() throws Exception {
        //given
        CreatePatientCommand createPatientCommand = getCreatePatientCommand();
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
        JavaType type = objectMapper.getTypeFactory().constructParametricType(TypeUtils.TestPageResult.class, PatientDto.class);
        TypeUtils.TestPageResult<PatientDto> response = objectMapper.readValue(content, type);

        assertThat(response.getContent().get(0).getEmail()).isEqualTo(createPatientCommand.getEmail());
    }

    @Test
    public void getPatientById() throws Exception {
        //given
        CreatePatientCommand createPatientCommand = getCreatePatientCommand();
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
    public void returnBadRequestWhenPatientAlreadyExist() throws Exception {
        CreatePatientCommand createPatientCommand = getCreatePatientCommand();
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

    private CreatePatientCommand getCreatePatientCommand() {
        return new CreatePatientCommand(
                "Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 1, "John", "John@email.com"
        );
    }
}

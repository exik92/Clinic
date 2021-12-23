package com.company.clinic.doctor;

import com.company.clinic.command.CreateDoctorCommand;
import com.company.clinic.common.IntegrationTest;
import com.company.clinic.dto.DoctorDto;
import com.company.clinic.model.doctor.MedicalAnimalSpecialization;
import com.company.clinic.model.doctor.MedicalSpecialization;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class DoctorIntegrationTests extends IntegrationTest {

    @Test
    public void getAllDoctors() throws Exception {
        //when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/doctors")
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<DoctorDto> doctors = objectMapper.readValue(content, new TypeReference<List<DoctorDto>>() {
        });
        assertThat(doctors).hasSize(0);
    }

    @Test
    public void getDoctorById() throws Exception {
        //given
        CreateDoctorCommand createDoctorCommand = getDoctorDto();
        DoctorDto dto = addDoctor(createDoctorCommand, jwtToken);

        //when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/doctors/" + dto.getId())
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String content = result.getResponse().getContentAsString();
        DoctorDto doctor = objectMapper.readValue(content, DoctorDto.class);
        assertThat(doctor.getFirstName()).isEqualTo(createDoctorCommand.getFirstName());
    }

    @Test
    public void returnBadRequestWhenDoctorAlreadyExist() throws Exception {
        CreateDoctorCommand createDoctorCommand = getDoctorDto();
        addDoctor(createDoctorCommand, jwtToken);

        // try again
        mvc.perform(MockMvcRequestBuilders
                        .post("/doctors")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(createDoctorCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void fireDoctor() throws Exception {
        //given
        CreateDoctorCommand createDoctorCommand = getDoctorDto();
        DoctorDto dto = addDoctor(createDoctorCommand, jwtToken);

        //when
        mvc.perform(MockMvcRequestBuilders
                .put("/doctors/fire/" + dto.getId())
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/doctors")
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String content = result.getResponse().getContentAsString();
        List<DoctorDto> doctors = objectMapper.readValue(content, new TypeReference<List<DoctorDto>>() {
        });
        assertThat(doctors).hasSize(0);
    }

    @Test
    public void getListOfDoctors() throws Exception {
        //given
        CreateDoctorCommand createDoctorCommand = getDoctorDto();
        addDoctor(createDoctorCommand, jwtToken);

        //when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/doctors")
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String content = result.getResponse().getContentAsString();
        List<DoctorDto> doctors = objectMapper.readValue(content, new TypeReference<List<DoctorDto>>() {
        });
        assertThat(doctors.get(0).getFirstName()).isEqualTo(createDoctorCommand.getFirstName());
    }



    private CreateDoctorCommand getDoctorDto() {
        return new CreateDoctorCommand(
                "John", "Doe", MedicalSpecialization.DERMATOLOGY, MedicalAnimalSpecialization.BIRD, 100, 100123332
        );
    }
}

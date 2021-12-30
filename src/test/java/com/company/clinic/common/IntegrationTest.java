package com.company.clinic.common;

import com.company.clinic.command.CreateDoctorCommand;
import com.company.clinic.command.CreatePatientCommand;
import com.company.clinic.command.CreateVisitCommand;
import com.company.clinic.dto.DoctorDto;
import com.company.clinic.dto.PatientDto;
import com.company.clinic.dto.VisitDto;
import com.company.clinic.repository.DoctorRepository;
import com.company.clinic.repository.PatientRepository;
import com.company.clinic.repository.VisitRepository;
import com.company.clinic.repository.VisitTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.company.clinic.util.AuthorizationUtil.createUserAndGetJwtToken;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("/credentials.properties")
public abstract class IntegrationTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    private VisitTokenRepository visitTokenRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Value("${test.username}")
    protected String username;

    @Value("${test.password}")
    protected String password;

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected String jwtToken = "";

    @BeforeEach
    public void initEach() throws Exception {
        visitTokenRepository.deleteAll();
        visitRepository.deleteAll();
        patientRepository.deleteAll();
        doctorRepository.deleteAll();
        jwtToken = createUserAndGetJwtToken(mvc, objectMapper, username, password);
    }

    protected VisitDto addVisit(CreateVisitCommand createVisitCommand, String jwtToken) throws Exception {
        String content = mvc.perform(MockMvcRequestBuilders
                        .post("/visits")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(createVisitCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(content, VisitDto.class);
    }

    protected PatientDto addPatient(CreatePatientCommand createPatientCommand, String jwtToken) throws Exception {
        String content = mvc.perform(MockMvcRequestBuilders
                        .post("/patients")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(createPatientCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readValue(content, PatientDto.class);
    }

    protected DoctorDto addDoctor(CreateDoctorCommand createDoctorCommand, String jwtToken) throws Exception {
        String content = mvc.perform(MockMvcRequestBuilders
                        .post("/doctors")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(createDoctorCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readValue(content, DoctorDto.class);
    }

}

package com.company.clinic.util;

import com.company.clinic.command.CreateUserCommand;
import com.company.clinic.dto.JwtDto;
import com.company.clinic.command.LoginUserCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class AuthorizationUtil {

    public static String createUserAndGetJwtToken(MockMvc mvc, ObjectMapper objectMapper, String username, String password) throws Exception {
        CreateUserCommand createUserCommand = new CreateUserCommand(username, "john@email.com", password);
        LoginUserCommand loginUserCommand = new LoginUserCommand(createUserCommand.getUsername(), createUserCommand.getPassword());

        mvc.perform(MockMvcRequestBuilders
                .post("/register")
                .content(objectMapper.writeValueAsString(createUserCommand))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/login")
                .content(objectMapper.writeValueAsString(loginUserCommand))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JwtDto jwtDto = objectMapper.readValue(content, JwtDto.class);
        return jwtDto.getAccessToken();
    }
}


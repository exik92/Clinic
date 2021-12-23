package com.company.clinic.controller;

import com.company.clinic.command.LoginUserCommand;
import com.company.clinic.command.CreateUserCommand;
import com.company.clinic.dto.JwtDto;
import com.company.clinic.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public void signUp(@RequestBody CreateUserCommand createUserCommand) {
        authenticationService.register(createUserCommand);
    }

    @PostMapping("/login")
    public JwtDto authenticateUser(@RequestBody LoginUserCommand loginUserCommand) {
        return authenticationService.login(loginUserCommand);
    }
}
package com.company.clinic.command;

import com.company.clinic.validation.register.UserAlreadyExists;

import javax.validation.constraints.NotBlank;

@UserAlreadyExists
public class CreateUserCommand {

    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public CreateUserCommand() {
    }

    public CreateUserCommand(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

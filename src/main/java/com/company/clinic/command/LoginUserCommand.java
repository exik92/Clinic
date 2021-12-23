package com.company.clinic.command;

import javax.validation.constraints.NotBlank;

public class LoginUserCommand {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public LoginUserCommand() {
    }

    public LoginUserCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
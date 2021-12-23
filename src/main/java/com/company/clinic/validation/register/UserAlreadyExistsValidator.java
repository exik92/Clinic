package com.company.clinic.validation.register;

import com.company.clinic.command.CreateUserCommand;
import com.company.clinic.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
class UserAlreadyExistsValidator implements ConstraintValidator<UserAlreadyExists, CreateUserCommand> {
    private final UserRepository userRepository;

    public UserAlreadyExistsValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(CreateUserCommand value, ConstraintValidatorContext context) {
        if (userRepository.existsByEmail(value.getEmail())) {
            return false;
        }
        if (userRepository.existsByUsername(value.getUsername())) {
            return false;
        }
        return true;
    }
}

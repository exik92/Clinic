package com.company.clinic.service;

import com.company.clinic.command.LoginUserCommand;
import com.company.clinic.command.CreateUserCommand;
import com.company.clinic.dto.JwtDto;
import com.company.clinic.model.security.Role;
import com.company.clinic.model.security.RoleName;
import com.company.clinic.model.security.User;
import com.company.clinic.repository.RoleRepository;
import com.company.clinic.repository.UserRepository;
import com.company.clinic.security.jwt.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public void register(CreateUserCommand createUserCommand) {
        User user = new User(createUserCommand.getUsername(), createUserCommand.getEmail(), encoder.encode(createUserCommand.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER);
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);
    }

    @Transactional
    public JwtDto login(LoginUserCommand loginUserCommand) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserCommand.getUsername(), loginUserCommand.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new JwtDto(jwt, userDetails.getUsername());
    }
}

package com.gindho.service;

import com.gindho.dto.AuthenticationRequest;
import com.gindho.dto.AuthenticationResponse;
import com.gindho.dto.UserDto;
import com.gindho.model.Role;
import com.gindho.model.User;
import com.gindho.repository.UserRepository;
import com.gindho.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(AuthenticationRequest request) {
        if (userRepository.existsByUsername(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        var user = User.builder()
                .nom(request.getEmail().split("@")[0])
                .prenom("Utilisateur")
                .email(request.getEmail())
                .motDePasseHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.PATIENT)
                .actif(true)
                .build();

        user = userRepository.save(user);

        var jwtToken = jwtService.generateToken(user, user.getRole().name());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().name())
                .userId(user.getId())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUsername(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var jwtToken = jwtService.generateToken(user, user.getRole().name());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().name())
                .userId(user.getId())
                .build();
    }

    public UserDto updatePermissions(Long id, String[] permissions) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.save(user);
        return convertToDto(user);
    }

    public void changeRole(Long id, Role role) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    public void resetPassword(String email) {
        var user = userRepository.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .role(user.getRole().name())
                .actif(user.isActif())
                .creeLe(user.getCreeLe())
                .build();
    }
}

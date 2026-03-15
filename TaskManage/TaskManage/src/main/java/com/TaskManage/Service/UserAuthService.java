package com.TaskManage.Service;

import com.TaskManage.DTO.*;
import com.TaskManage.Entity.UserAuth;
import com.TaskManage.Enum.Role;
import com.TaskManage.Repository.UserAuthRepository;
import com.TaskManage.Security.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserAuthRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponseDTO(null, "Email already exists");
        }
        
        UserAuth user = new UserAuth();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        
        return new AuthResponseDTO(null, "User registered successfully");
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        UserAuth user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponseDTO(null, "Invalid credentials");
        }
        
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO(token, "Login successful");
    }
}

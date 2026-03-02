package com.raghav.xclone.user.service;


import com.raghav.xclone.security.JwtService;
import com.raghav.xclone.user.dto.LoginDTO;
import com.raghav.xclone.user.dto.RegisteringDTO;
import com.raghav.xclone.user.entity.Role;
import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    public User register(RegisteringDTO dto) {

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(Role.USER);
        user.setEmail(dto.getEmail());
        user.setEnabled(true);

        user.setCreatedAt(LocalDateTime.now());
        return repo.save(user);
    }

    public String verify(LoginDTO dto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                dto.getUsername(),
                                dto.getPassword()
                        )
                );
        User user = repo.findByUsername(dto.getUsername());

        return jwtService.genrateToken(user.getUsername(),user.getRole().name());
    }
}

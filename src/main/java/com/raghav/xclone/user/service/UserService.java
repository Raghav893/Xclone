package com.raghav.xclone.user.service;


import com.raghav.xclone.security.JwtService;
import com.raghav.xclone.user.dto.LoginDTO;
import com.raghav.xclone.user.dto.RegisteringDTO;
import com.raghav.xclone.user.dto.UpdateProfileDTO;
import com.raghav.xclone.user.entity.Role;
import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

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
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        user.setEnabled(true);
        user.setFollowers_count(0);
        user.setFollowing_count(0);

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
    public String updateProfile(UpdateProfileDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = repo.findByUsername(username);
        if (user == null){
            throw new RuntimeException("user not found");
        }
        user.setBio(dto.getBio());
        user.setProfile_image_url(dto.getProfile_image_url());
        user.setUsername(dto.getUsername());
        repo.save(user);
        return "Profile updated successfully";
    }
    public User getMyProfile(){
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user =repo.findByUsername(username);

        if (user == null){
            throw new RuntimeException("user not found");
        }
        return user;
    }
    public List<User> searchUser(String query, int page, int size) {

        Pageable pageable = (Pageable) PageRequest.of(
                page,
                size,
                Sort.by("username").ascending()
        );

        Page<User> userPage =
                repo.findByUsernameContainingIgnoreCase(query, pageable);

        return userPage.getContent();
    }
}

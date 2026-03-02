package com.raghav.xclone.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true)

    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    @Email
    private String email;

    private LocalDateTime createdAt;

    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private boolean enabled;

    private String bio;

    private String profile_image_url;

    private Long followers_count;

    private Long following_count;

}

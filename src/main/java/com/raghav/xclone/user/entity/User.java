package com.raghav.xclone.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true)

    private String username;

    @Column(nullable = false)
    @JsonIgnore
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

    private Integer followers_count;

    private Integer following_count;

}

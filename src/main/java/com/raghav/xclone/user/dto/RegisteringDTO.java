package com.raghav.xclone.user.dto;

import com.raghav.xclone.user.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisteringDTO {
  private   String username;
    private String password;
    private Role Role;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
}

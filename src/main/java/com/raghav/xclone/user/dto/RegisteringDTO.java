package com.raghav.xclone.user.dto;

import com.raghav.xclone.user.entity.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisteringDTO {
    String username;
    String password;
    Role Role;
    @Email
    String Email;
}

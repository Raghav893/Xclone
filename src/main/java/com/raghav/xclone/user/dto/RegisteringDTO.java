package com.raghav.xclone.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisteringDTO {
    String username;
    String password;
    String Role;
    String Email;
}

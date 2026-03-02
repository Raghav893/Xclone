package com.raghav.xclone.user.dto;

import lombok.Data;

@Data
public class UpdateProfileDTO {
    private String username;
    private String bio;
    private String profile_image_url;
}

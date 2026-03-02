package com.raghav.xclone.user.controller;


import com.raghav.xclone.common.response.ApiResponse;
import com.raghav.xclone.user.dto.LoginDTO;
import com.raghav.xclone.user.dto.RegisteringDTO;
import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody RegisteringDTO dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        User user = userService.register(dto);
        ApiResponse<User> response = new ApiResponse<>(
                true,
                "user registered succesfully",
                user,
                null
        );
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDTO dto) {
        String jwt = userService.verify(dto);
        ApiResponse<String> response = new ApiResponse<>(
                true,
                "jwt token: ",
                jwt,
                null

        );
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
}

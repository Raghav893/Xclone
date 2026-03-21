package com.raghav.xclone.user.controller;


import com.raghav.xclone.common.response.ApiResponse;
import com.raghav.xclone.Like.service.LikeService;
import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.user.dto.LoginDTO;
import com.raghav.xclone.user.dto.RegisteringDTO;
import com.raghav.xclone.user.dto.UpdateProfileDTO;
import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @PostMapping("/auth/register")
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

    @PostMapping("/auth/login")
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

    @PutMapping("/users/me")
    public ResponseEntity<ApiResponse<String>> updateProfile(@RequestBody UpdateProfileDTO dto) {
        ApiResponse<String> response = new ApiResponse<>(
                true,
                "executed",
                userService.updateProfile(dto),
                null
        );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/user/me")
    public ResponseEntity<ApiResponse<User>> getMyProfile(){
        ApiResponse<User> response = new ApiResponse<>(
                true,
                "found",
                userService.getMyProfile(),
                null
        );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<ApiResponse<User>> getUserProfile(@PathVariable String username) {
        ApiResponse<User> response = new ApiResponse<>(
                true,
                "found",
                userService.getProfileByUsername(username),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{username}/likes")
    public ResponseEntity<ApiResponse<List<Tweet>>> getLikedTweetsByUser(
            @PathVariable String username) {
        ApiResponse<List<Tweet>> response = new ApiResponse<>(
                true,
                "liked tweets",
                likeService.getLikedTweetsByUsername(username),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/search")
    public ResponseEntity<ApiResponse<List<User>>> searchUser(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<User> users = userService.searchUser(query, page, size);

        ApiResponse<List<User>> response =
                new ApiResponse<>(true, "Users found", users, null);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        ApiResponse<String> response = new ApiResponse<>(
                true,
                "logged out",
                "Client should discard the JWT; server is stateless.",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

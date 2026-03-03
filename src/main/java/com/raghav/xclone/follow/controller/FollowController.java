package com.raghav.xclone.follow.controller;

import com.raghav.xclone.common.response.ApiResponse;
import com.raghav.xclone.follow.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<ApiResponse<String>> followsend(@PathVariable String username) {
        followService.followUser(username);
        ApiResponse<String> response = new ApiResponse<>(
                true,
                "executed",
                "followed user" + username,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

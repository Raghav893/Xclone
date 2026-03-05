package com.raghav.xclone.follow.controller;

import com.raghav.xclone.common.response.ApiResponse;
import com.raghav.xclone.follow.service.FollowService;
import com.raghav.xclone.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{username}/follow")
    public ResponseEntity<ApiResponse<User>> unfollowUser(@PathVariable String username) {
        User user=followService.unFollow(username);
        ApiResponse response = new ApiResponse<>(
                true,
                "unfollwed user"+username,
                user
                ,
                null
        );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<ApiResponse<List<User>>> followers(@PathVariable String username) {
        List<User> followerList= followService.followerList(username);

        ApiResponse<List<User>> response = new ApiResponse<>(
          true,
          "Followers list",
          followerList,
          null
        );

        return  ResponseEntity.ok(response);
    }
    @GetMapping("/{username}/following")
    public ResponseEntity<ApiResponse<List<User>>> following(@PathVariable String username) {
        List<User> followerList= followService.followingList(username);

        ApiResponse<List<User>> response = new ApiResponse<>(
                true,
                "Following list",
                followerList,
                null
        );

        return  ResponseEntity.ok(response);
    }

}

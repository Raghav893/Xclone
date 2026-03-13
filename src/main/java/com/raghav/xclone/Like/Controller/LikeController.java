package com.raghav.xclone.Like.Controller;

import com.raghav.xclone.Like.service.LikeService;
import com.raghav.xclone.common.response.ApiResponse;
import com.raghav.xclone.tweet.entity.Tweet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tweets")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<ApiResponse<Tweet>> LikeTweet(@PathVariable UUID id) {
        ApiResponse response = new ApiResponse<>(
                true,
                "Liked Tweet",
                likeService.LikeTweetById(id),
                null
        );
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}/like")
    public ResponseEntity<ApiResponse<Tweet>> UnLikeTweet(@PathVariable UUID id) {
        ApiResponse response = new ApiResponse<>(
                true,
                "UnLiked Tweet",
                likeService.UnLikeTweet(id),
                null
        );
        return ResponseEntity.ok(response);
    }
}

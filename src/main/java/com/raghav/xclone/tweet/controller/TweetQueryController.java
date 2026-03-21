package com.raghav.xclone.tweet.controller;

import com.raghav.xclone.common.response.ApiResponse;
import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.tweet.service.TweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tweets")
public class TweetQueryController {
    private final TweetService tweetService;

    public TweetQueryController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Tweet>> getTweetById(@PathVariable UUID id) {
        ApiResponse<Tweet> response = new ApiResponse<>(
                true,
                "Tweet",
                tweetService.getTweetById(id),
                null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/feed")
    public ResponseEntity<ApiResponse<List<Tweet>>> getFeed(
            @RequestParam(defaultValue = "0") int page
    ) {
        ApiResponse<List<Tweet>> response = new ApiResponse<>(
                true,
                "Feed",
                tweetService.getFeed(page),
                null
        );
        return ResponseEntity.ok(response);
    }
}

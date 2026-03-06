package com.raghav.xclone.tweet.controller;

import com.raghav.xclone.common.response.ApiResponse;
import com.raghav.xclone.tweet.dto.TweetDTO;
import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.tweet.service.TweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/users/tweet")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Tweet>> CreateTweet(@RequestBody TweetDTO tweetDTO) {
        Tweet tweet =tweetService.CreateTweet(tweetDTO);
        ApiResponse response = new ApiResponse<>();
        response.setData(tweet);
        response.setErrors(null);
        response.setMessage("Created tweet");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
}

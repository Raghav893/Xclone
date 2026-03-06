package com.raghav.xclone.tweet.controller;

import com.raghav.xclone.common.response.ApiResponse;
import com.raghav.xclone.tweet.dto.ReplyDTO;
import com.raghav.xclone.tweet.dto.TweetDTO;
import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.tweet.service.TweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @PostMapping("/{parentId}/reply")
    public ResponseEntity<ApiResponse<Tweet>> ReplyTweet(@RequestBody ReplyDTO dto, @PathVariable UUID parentId) {
        Tweet tweet =tweetService.ReplyTweet(dto,parentId);
        ApiResponse response = new ApiResponse<>();
        response.setData(tweet);
        response.setErrors(null);
        response.setMessage("Replied tweet");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<List<Tweet>>> getTweetsByUsername(@PathVariable String username) {
        ApiResponse response = new ApiResponse<>();
                response.setData(tweetService.GetTweetByUser(username));
                response.setSuccess(true);
                response.setErrors(null);
                response.setMessage("Tweets by "+username);
                return ResponseEntity.ok(response);

    }
}

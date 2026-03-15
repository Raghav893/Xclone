package com.raghav.xclone.Retweet.Controller;

import com.raghav.xclone.Retweet.Entity.Retweet;
import com.raghav.xclone.Retweet.Service.RetweetService;
import com.raghav.xclone.common.response.ApiResponse;
import com.raghav.xclone.tweet.entity.Tweet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tweets")
public class RetweetController {

    private final RetweetService retweetService;

    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping("/{id}/retweet")
    public ResponseEntity<ApiResponse<Tweet>> retweetTweet(@PathVariable UUID id) {
        ApiResponse<Tweet> response = new ApiResponse<>(
                true,
                "Retweeted successfully",
                retweetService.retweetTweet(id),
                null
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/retweet")
    public ResponseEntity<ApiResponse<Tweet>> undoRetweet(@PathVariable UUID id) {
        ApiResponse<Tweet> response = new ApiResponse<>(
                true,
                "Retweet removed successfully",
                retweetService.undoRetweet(id),
                null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/retweet")
    public ResponseEntity<ApiResponse<List<Retweet>>> getRetweets(@PathVariable UUID id) {
        ApiResponse<List<Retweet>> response = new ApiResponse<>(
                true,
                "Retweets fetched successfully",
                retweetService.getRetweetsOfTweet(id),
                null
        );
        return ResponseEntity.ok(response);
    }
}

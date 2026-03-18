
package com.raghav.xclone.hashtag.Controller;

import com.raghav.xclone.common.response.ApiResponse;
import com.raghav.xclone.hashtag.DTO.HashTagDTO;
import com.raghav.xclone.hashtag.service.HashtagService;
import com.raghav.xclone.tweet.entity.Tweet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class HashtagController {

    private final HashtagService hashtagService;

    public HashtagController(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
    }

    @GetMapping("/hashtag/{tag}")
    public ResponseEntity<ApiResponse<List<Tweet>>> getTweetByHashTag(@PathVariable String tag) {
        HashTagDTO hashtagDTO = new HashTagDTO();
        hashtagDTO.setTag(tag);

        ApiResponse<List<Tweet>> response = new ApiResponse<>(
                true,
                "Tweets fetched successfully",
                hashtagService.getTweetsByHashTag(hashtagDTO),
                null
        );
        return ResponseEntity.ok(response);
    }
}

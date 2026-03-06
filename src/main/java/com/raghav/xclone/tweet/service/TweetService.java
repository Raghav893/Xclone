package com.raghav.xclone.tweet.service;

import com.raghav.xclone.tweet.repo.TweetRepository;
import org.springframework.stereotype.Service;

@Service
public class TweetService {
    private final TweetRepository tweetRepo;

    public TweetService(TweetRepository tweetRepo) {
        this.tweetRepo = tweetRepo;
    }

}

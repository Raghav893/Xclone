package com.raghav.xclone.tweet.service;

import com.raghav.xclone.tweet.dto.TweetDTO;
import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.tweet.repo.TweetRepository;
import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TweetService {
    private final TweetRepository tweetRepo;
    private final UserRepository userRepository;
    public TweetService(TweetRepository tweetRepo, UserRepository userRepository) {
        this.tweetRepo = tweetRepo;
        this.userRepository = userRepository;
    }
    @Transactional
    public Tweet CreateTweet(TweetDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new RuntimeException("User not found");
        }

        Tweet tweet = new Tweet();
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setContent(dto.getContent());
        tweet.setParentTweet(null);
        tweet.setMediaUrl(dto.getMediaurl());
        tweet.setAuthor(user);
        tweetRepo.save(tweet);
        return tweet;
    }

}

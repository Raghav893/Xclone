package com.raghav.xclone.Like.service;

import com.raghav.xclone.Like.Repo.LikeRepository;
import com.raghav.xclone.Like.entity.Like;
import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.tweet.repo.TweetRepository;
import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.repo.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    public LikeService(LikeRepository likeRepository, TweetRepository tweetRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    @CacheEvict(cacheNames = "feed", allEntries = true)
    public Tweet LikeTweetById(UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  authentication.getName();
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new RuntimeException("user not found");
        }
        Tweet tweet = tweetRepository.findTweetByTweetId(id);
        if (tweet == null) {
            throw new RuntimeException("Tweet Not found");
        }
        if (likeRepository.existsByUserAndTweet(currentUser, tweet)) {
            throw new RuntimeException("You have already liked this tweet");
        }
        tweet.setLikeCount(tweet.getLikeCount()+1);
        tweetRepository.save(tweet);
        Like like = new Like();
        like.setTweet(tweet);
        like.setUser(currentUser);
        like.setCreatedAt(LocalDateTime.now());
        likeRepository.save(like);
        return tweet;
    }
    @Transactional
    @CacheEvict(cacheNames = "feed", allEntries = true)
    public Tweet UnLikeTweet(UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  authentication.getName();
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new RuntimeException("user not found");
        }
        Tweet tweet = tweetRepository.findTweetByTweetId(id);
        if (tweet == null) {
            throw new RuntimeException("Tweet Not found");
        }
        Like like = likeRepository.findByUserAndTweet(currentUser, tweet)
                .orElseThrow(() -> new RuntimeException("Like not found"));
        tweet.setLikeCount(Math.max(0, tweet.getLikeCount()-1));
        tweetRepository.save(tweet);
        likeRepository.delete(like);
        return tweet;
    }
    public List<Like> GetLikesByTweetId(UUID id){
        Tweet tweet = tweetRepository.findTweetByTweetId(id);
        if (tweet == null) {
            throw new RuntimeException("Tweet Not found");
        }
        return likeRepository.getLikesByTweet(tweet);
    }

    public List<Tweet> getLikedTweetsByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("user not found");
        }
        return likeRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(Like::getTweet)
                .collect(Collectors.toList());
    }
}

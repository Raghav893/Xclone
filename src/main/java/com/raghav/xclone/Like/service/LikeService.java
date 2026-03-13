package com.raghav.xclone.Like.service;

import com.raghav.xclone.Like.Repo.LikeRepository;
import com.raghav.xclone.Like.entity.Like;
import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.tweet.repo.TweetRepository;
import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        tweet.setLikeCount(tweet.getLikeCount()-1);
        tweetRepository.save(tweet);
        likeRepository.deleteLikeByUser(currentUser);
        return tweet;
    }
    public List<Like> GetLikesByTweetId(UUID id){
        Tweet tweet = tweetRepository.findTweetByTweetId(id);
        if (tweet == null) {
            throw new RuntimeException("Tweet Not found");
        }
        return likeRepository.getLikesByTweet(tweet);
    }
}

package com.raghav.xclone.Retweet.Service;

import com.raghav.xclone.Retweet.Entity.Retweet;
import com.raghav.xclone.Retweet.Repo.RetweetRepository;
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
public class RetweetService {

    private final RetweetRepository retweetRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public RetweetService(RetweetRepository retweetRepository,
                          TweetRepository tweetRepository,
                          UserRepository userRepository) {
        this.retweetRepository = retweetRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    private Tweet getTweetById(UUID tweetId) {
        Tweet tweet = tweetRepository.findTweetByTweetId(tweetId);
        if (tweet == null) {
            throw new RuntimeException("Tweet not found");
        }
        return tweet;
    }

    public Tweet retweetTweet(UUID tweetId) {
        User currentUser = getCurrentUser();
        Tweet tweet = getTweetById(tweetId);

        if (retweetRepository.existsByUserAndTweet(currentUser, tweet)) {
            throw new RuntimeException("You have already retweeted this tweet");
        }

        Retweet retweet = new Retweet();
        retweet.setUser(currentUser);
        retweet.setTweet(tweet);
        retweet.setCreatedAt(LocalDateTime.now());
        retweetRepository.save(retweet);

        tweet.setRetweetCount(tweet.getRetweetCount() + 1);
        tweetRepository.save(tweet);

        return tweet;
    }

    @Transactional
    public Tweet undoRetweet(UUID tweetId) {
        User currentUser = getCurrentUser();
        Tweet tweet = getTweetById(tweetId);

        Retweet retweet = retweetRepository.findByUserAndTweet(currentUser, tweet)
                .orElseThrow(() -> new RuntimeException("Retweet not found"));

        retweetRepository.delete(retweet);

        tweet.setRetweetCount(Math.max(0, tweet.getRetweetCount() - 1));
        tweetRepository.save(tweet);

        return tweet;
    }

    public List<Retweet> getRetweetsOfTweet(UUID tweetId) {
        Tweet tweet = getTweetById(tweetId);
        return retweetRepository.findAllByTweet(tweet);
    }
}

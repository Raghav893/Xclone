package com.raghav.xclone.tweet.repo;

import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TweetRepository extends JpaRepository<Tweet, UUID> {
    Tweet findTweetByTweetId(UUID tweetId);

    List<Tweet> findByAuthor(User author);
}

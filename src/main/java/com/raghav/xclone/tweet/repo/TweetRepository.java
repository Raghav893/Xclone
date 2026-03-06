package com.raghav.xclone.tweet.repo;

import com.raghav.xclone.tweet.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TweetRepository extends JpaRepository<Tweet, UUID> {
}

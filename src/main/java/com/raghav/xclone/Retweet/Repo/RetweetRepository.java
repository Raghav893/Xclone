package com.raghav.xclone.Retweet.Repo;

import com.raghav.xclone.Retweet.Entity.Retweet;
import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet, UUID> {

    Optional<Retweet> findByUserAndTweet(User user, Tweet tweet);

    List<Retweet> findAllByTweet(Tweet tweet);

    boolean existsByUserAndTweet(User user, Tweet tweet);
}

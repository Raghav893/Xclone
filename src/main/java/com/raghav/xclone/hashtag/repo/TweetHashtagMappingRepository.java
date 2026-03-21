package com.raghav.xclone.hashtag.repo;

import com.raghav.xclone.hashtag.Entity.Hashtag;
import com.raghav.xclone.hashtag.Entity.TweetHashtagMapping;
import com.raghav.xclone.tweet.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TweetHashtagMappingRepository extends JpaRepository<TweetHashtagMapping, UUID> {

    @Query("select distinct m.tweet from TweetHashtagMapping m where m.hashtag = :hashtag")
    List<Tweet> findByHashtag(@Param("hashtag") Hashtag hashtag);

    void deleteByTweet(Tweet tweet);
}

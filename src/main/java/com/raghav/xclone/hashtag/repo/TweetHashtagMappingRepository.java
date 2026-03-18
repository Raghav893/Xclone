package com.raghav.xclone.hashtag.repo;

import com.raghav.xclone.hashtag.Entity.Hashtag;
import com.raghav.xclone.hashtag.Entity.TweetHashtagMapping;
import com.raghav.xclone.tweet.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TweetHashtagMappingRepository extends JpaRepository<TweetHashtagMapping, UUID> {

    List<Tweet> findByHashtag(Hashtag hashtag);
}

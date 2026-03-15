package com.raghav.xclone.hashtag.repo;

import com.raghav.xclone.hashtag.Entity.TweetHashtagMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TweetHashtagMappingRepository extends JpaRepository<TweetHashtagMapping, UUID> {
}

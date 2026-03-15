package com.raghav.xclone.hashtag.repo;

import com.raghav.xclone.hashtag.Entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HashtagRepository extends JpaRepository<Hashtag, UUID> {
}

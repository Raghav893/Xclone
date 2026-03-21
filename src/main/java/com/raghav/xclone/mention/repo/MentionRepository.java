package com.raghav.xclone.mention.repo;

import com.raghav.xclone.mention.entity.Mention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MentionRepository extends JpaRepository<Mention, UUID> {
}

package com.raghav.xclone.Like.Repo;

import com.raghav.xclone.Like.entity.Like;
import com.raghav.xclone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {
    void deleteLikeByUser(User user);
}

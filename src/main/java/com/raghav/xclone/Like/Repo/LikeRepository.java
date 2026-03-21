package com.raghav.xclone.Like.Repo;

import com.raghav.xclone.Like.entity.Like;
import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {
    boolean existsByUserAndTweet(User user, Tweet tweet);

    java.util.Optional<Like> findByUserAndTweet(User user, Tweet tweet);

    void deleteLikeByUser(User user);

    List<Like> getLikesByTweet(Tweet tweet);

    List<Like> findByUserOrderByCreatedAtDesc(User user);
}

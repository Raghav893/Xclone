package com.raghav.xclone.follow.repo;

import com.raghav.xclone.follow.entity.Follow;
import com.raghav.xclone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface followRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(User currentUser, User targetUser);



    Follow findByFollowerAndFollowing(User follower, User following);

    List<Follow> findByFollower(User follower);
}

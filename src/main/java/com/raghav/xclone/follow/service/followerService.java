package com.raghav.xclone.follow.service;

import com.raghav.xclone.follow.entity.Follow;
import com.raghav.xclone.follow.repo.followRepository;

import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class followerService {
    private final followRepository followRepository;
    private final UserRepository userRepository;

    public followerService(followRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public void followUser(String toFollowUsername) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();

        User currentUser = userRepository.findByUsername(currentUsername);
        User targetUser = userRepository.findByUsername(toFollowUsername);

        if (currentUser == null || targetUser == null) {
            throw new RuntimeException("User not found");
        }

        if (currentUser.getId().equals(targetUser.getId())) {
            throw new RuntimeException("You cannot follow yourself");
        }

        boolean alreadyFollowing =
                followRepository.existsByFollowerAndFollowing(currentUser, targetUser);

        if (alreadyFollowing) {
            throw new RuntimeException("Already following this user");
        }

        Follow follow = new Follow();
        follow.setFollower(currentUser);
        follow.setFollowing(targetUser);
        followRepository.save(follow);

        currentUser.setFollowing_count(currentUser.getFollowing_count() + 1);
        targetUser.setFollowers_count(targetUser.getFollowers_count() + 1);

        userRepository.save(currentUser);
        userRepository.save(targetUser);
    }
    }


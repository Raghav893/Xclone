package com.raghav.xclone.follow.service;

import com.raghav.xclone.follow.entity.Follow;
import com.raghav.xclone.follow.repo.followRepository;

import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowService {
    private final followRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(followRepository followRepository, UserRepository userRepository) {
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
    public User unFollow(String username){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();

        User currentUser = userRepository.findByUsername(currentUsername);
        User targetUser = userRepository.findByUsername(username);

        if (currentUser == null || targetUser == null) {
            throw new RuntimeException("User not found");
        }

        if (currentUser.getId().equals(targetUser.getId())) {
            throw new RuntimeException("You cannot follow yourself");
        }
        boolean follows = followRepository.existsByFollowerAndFollowing(currentUser,targetUser);
        if (!follows){
            throw new RuntimeException("You dont follow him");
        }
        Follow follow = followRepository.findByFollowerAndFollowing(currentUser,targetUser);
        currentUser.setFollowing_count(currentUser.getFollowing_count()-1);
        targetUser.setFollowers_count(targetUser.getFollowers_count()-1);
        userRepository.save(currentUser);
        userRepository.save(targetUser);
        followRepository.delete(follow);
        return currentUser;
    }
    public List<User> followingList(String username){


        User user = userRepository.findByUsername(username);


        if (user == null ) {
            throw new RuntimeException("User not found");
        }
        List<Follow> follows = followRepository.findByFollower(user);
        List<User> followingList =  follows
                .stream()
                .map(Follow::getFollowing)
                .toList();
        return followingList;
    }
    public List<User> followerList(String usermane){


        User user = userRepository.findByUsername(usermane);


        if (user == null ) {
            throw new RuntimeException("User not found");
        }
        List<Follow> follows = followRepository.findByFollower(user);
        List<User> followerList =  follows
                .stream()
                .map(Follow::getFollower)
                .toList();
        return followerList;
    }
}


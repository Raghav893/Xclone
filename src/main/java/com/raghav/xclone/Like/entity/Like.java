package com.raghav.xclone.Like.entity;

import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Like {
    @Id
    private UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private  User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id",nullable = false)
    private Tweet tweet;

    private LocalDateTime createdAt=LocalDateTime.now();
}

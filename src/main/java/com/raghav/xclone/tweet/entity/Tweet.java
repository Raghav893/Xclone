package com.raghav.xclone.tweet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.raghav.xclone.hashtag.Entity.Hashtag;
import com.raghav.xclone.mention.entity.Mention;
import com.raghav.xclone.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tweetId;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_tweet_id")
    private Tweet parentTweet;

    @Column(nullable = false)
    private int replyCount = 0;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private int retweetCount = 0;

    private String mediaUrl;
    @ManyToMany
    @JoinTable(
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> hashtags;
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private List<Mention> mentions;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

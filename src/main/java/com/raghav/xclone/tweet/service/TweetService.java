package com.raghav.xclone.tweet.service;

import com.raghav.xclone.hashtag.Entity.Hashtag;
import com.raghav.xclone.hashtag.service.HashtagService;

import com.raghav.xclone.mention.entity.Mention;
import com.raghav.xclone.mention.repo.MentionRepository;
import com.raghav.xclone.mention.service.MentionService;
import com.raghav.xclone.tweet.dto.ReplyDTO;
import com.raghav.xclone.tweet.dto.TweetDTO;
import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.tweet.repo.TweetRepository;
import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.repo.UserRepository;
import com.raghav.xclone.follow.entity.Follow;
import com.raghav.xclone.follow.repo.followRepository;
import com.raghav.xclone.hashtag.repo.TweetHashtagMappingRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TweetService {
    private static final int FEED_PAGE_SIZE = 35;
    private final TweetRepository tweetRepo;
    private final UserRepository userRepository;
    private final HashtagService hashtagService;
    private final MentionService mentionService;
    private final MentionRepository mentionRepository;
    private final TweetHashtagMappingRepository tweetHashtagMappingRepository;
    private final followRepository followRepository;
    public TweetService(TweetRepository tweetRepo,
                        UserRepository userRepository,
                        HashtagService hashtagService,
                        MentionService mentionService,
                        MentionRepository mentionRepository,
                        TweetHashtagMappingRepository tweetHashtagMappingRepository,
                        followRepository followRepository) {
        this.tweetRepo = tweetRepo;
        this.userRepository = userRepository;
        this.hashtagService = hashtagService;
        this.mentionService = mentionService;
        this.mentionRepository = mentionRepository;
        this.tweetHashtagMappingRepository = tweetHashtagMappingRepository;
        this.followRepository = followRepository;
    }
    @Transactional
    @CacheEvict(cacheNames = "feed", allEntries = true)
    public Tweet CreateTweet(TweetDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Tweet tweet = new Tweet();
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setContent(dto.getContent());
        tweet.setParentTweet(null);
        tweet.setMediaUrl(dto.getMediaurl());
        tweet.setAuthor(user);

        Tweet savedTweet = tweetRepo.save(tweet);

        List<Hashtag> hashtags = hashtagService.getHashtagFromTweet(savedTweet);
        List<Mention> mentions = mentionService.processMentions(savedTweet);

        savedTweet.setHashtags(hashtags);
        savedTweet.setMentions(mentions);

        return savedTweet;
    }
    @Transactional
    @CacheEvict(cacheNames = "feed", allEntries = true)
    public Tweet ReplyTweet(ReplyDTO dto, UUID parentId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new RuntimeException("User not found");
        }
        Tweet parentTweet = tweetRepo.findTweetByTweetId(parentId);
        if (parentTweet == null) {
            throw new RuntimeException("Tweet not found");
        }
        Tweet tweet = new Tweet();
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setContent(dto.getContent());
        tweet.setParentTweet(parentTweet);
        tweet.setMediaUrl(dto.getMediaurl());
        tweet.setAuthor(user);
        tweet = tweetRepo.save(tweet);
        hashtagService.getHashtagFromTweet(tweet);
        return tweet;
    }
    public List<Tweet> GetTweetByUser(String username){
        User user = userRepository.findByUsername(username);

        if (user == null){
            throw new RuntimeException("User not found");
        }

        List<Tweet> allTweets = tweetRepo.findByAuthor(user);
        return allTweets;
    }

    public Tweet getTweetById(UUID id) {
        Tweet tweet = tweetRepo.findTweetByTweetId(id);
        if (tweet == null) {
            throw new RuntimeException("Tweet not found");
        }
        return tweet;
    }

    public List<Tweet> getReplies(UUID parentId) {
        Tweet parent = tweetRepo.findTweetByTweetId(parentId);
        if (parent == null) {
            throw new RuntimeException("Tweet not found");
        }
        return tweetRepo.findByParentTweetOrderByCreatedAtAsc(parent);
    }

    @Cacheable(
            cacheNames = "feed",
            key = "T(org.springframework.security.core.context.SecurityContextHolder).context.authentication.name + ':' + #page"
    )
    public List<Tweet> getFeed(int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new RuntimeException("User not found");
        }
        List<Follow> follows = followRepository.findByFollower(currentUser);
        List<User> authors = follows
                .stream()
                .map(Follow::getFollowing)
                .toList();
        java.util.ArrayList<User> allAuthors = new java.util.ArrayList<>(authors);
        allAuthors.add(currentUser);
        if (allAuthors.isEmpty()) {
            return List.of();
        }
        Pageable pageable = PageRequest.of(page, FEED_PAGE_SIZE);
        Page<Tweet> tweetPage = tweetRepo.findByAuthorInOrderByCreatedAtDesc(allAuthors, pageable);
        return tweetPage.getContent();
    }

    @Transactional
    @CacheEvict(cacheNames = "feed", allEntries = true)
    public Tweet editTweet(UUID id, TweetDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new RuntimeException("User not found");
        }

        Tweet tweet = tweetRepo.findTweetByTweetId(id);
        if (tweet == null) {
            throw new RuntimeException("Tweet not found");
        }
        if (!tweet.getAuthor().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        tweet.setContent(dto.getContent());
        tweet.setMediaUrl(dto.getMediaurl());
        Tweet saved = tweetRepo.save(tweet);

        tweetHashtagMappingRepository.deleteByTweet(saved);
        mentionRepository.deleteByTweet(saved);

        List<Hashtag> hashtags = hashtagService.getHashtagFromTweet(saved);
        List<Mention> mentions = mentionService.processMentions(saved);

        saved.setHashtags(hashtags);
        saved.setMentions(mentions);

        return saved;
    }
    @Transactional
    @CacheEvict(cacheNames = "feed", allEntries = true)
    public void DeleteTweetById(UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username);
        Tweet tweet = tweetRepo.findTweetByTweetId(id);
        if(currentUser == null || tweet == null){
            throw new RuntimeException(" User not exist or tweet not exist");
        }
        if (!tweet.getAuthor().getId().equals(currentUser.getId())){
            throw new RuntimeException("Unauthorized");
        }
        tweetRepo.delete(tweet);
    }
}

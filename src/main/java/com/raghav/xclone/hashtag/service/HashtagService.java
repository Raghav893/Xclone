package com.raghav.xclone.hashtag.service;

import com.raghav.xclone.hashtag.DTO.HashTagDTO;
import com.raghav.xclone.hashtag.Entity.Hashtag;
import com.raghav.xclone.hashtag.Entity.TweetHashtagMapping;
import com.raghav.xclone.hashtag.repo.HashtagRepository;
import com.raghav.xclone.hashtag.repo.TweetHashtagMappingRepository;
import com.raghav.xclone.tweet.entity.Tweet;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HashtagService {

    private final HashtagRepository hashtagRepository;
    private final TweetHashtagMappingRepository tweetHashtagMappingRepository;

    public HashtagService(HashtagRepository hashtagRepository,
                          TweetHashtagMappingRepository tweetHashtagMappingRepository) {
        this.hashtagRepository = hashtagRepository;
        this.tweetHashtagMappingRepository = tweetHashtagMappingRepository;
    }

    public List<Hashtag> getHashtagFromTweet(Tweet tweet) {
        String content = tweet.getContent().trim();

        List<Hashtag> hashtags = new ArrayList<>();
        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String tag = matcher.group(1).toLowerCase();

            Hashtag hashtag;
            try {
                Optional<Hashtag> optionalHashtag = hashtagRepository.findByTag(tag);
                if (optionalHashtag.isPresent()) {
                    hashtag = optionalHashtag.get();
                } else {
                    Hashtag newHashtag = new Hashtag();
                    newHashtag.setTag(tag);
                    newHashtag.setCreatedAt(LocalDateTime.now());
                    hashtag = hashtagRepository.save(newHashtag);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error occurred while processing hashtag: " + tag, e);
            }
            TweetHashtagMapping mapping = new TweetHashtagMapping();
            mapping.setTweet(tweet);
            mapping.setHashtag(hashtag);
            tweetHashtagMappingRepository.save(mapping);

            hashtags.add(hashtag);
        }

        return hashtags;
    }
    public List<Tweet> getTweetsByHashTag(HashTagDTO dto){
        if (dto == null || dto.getTag() == null) {
            return List.of();
        }

        String tag = dto.getTag().trim();
        if (tag.startsWith("#")) {
            tag = tag.substring(1);
        }
        if (tag.isBlank()) {
            return List.of();
        }
        tag = tag.toLowerCase();

        Optional<Hashtag> optionalHashtag = hashtagRepository.findByTag(tag);
        if (optionalHashtag.isEmpty()) {
            return List.of();
        }

        List<Tweet> tweets = tweetHashtagMappingRepository.findByHashtag(optionalHashtag.get());
        if (tweets == null || tweets.isEmpty()) {
            return List.of();
        }


        java.util.LinkedHashMap<java.util.UUID, Tweet> unique = new java.util.LinkedHashMap<>();
        for (Tweet t : tweets) {
            if (t != null && t.getTweetId() != null) {
                unique.putIfAbsent(t.getTweetId(), t);
            }
        }
        return new ArrayList<>(unique.values());
    }
}

package com.raghav.xclone.mention.service;


import com.raghav.xclone.mention.entity.Mention;
import com.raghav.xclone.mention.repo.MentionRepository;
import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MentionService {
    private final MentionRepository mentionRepository;
    private final UserRepository userRepository;

    public MentionService(MentionRepository mentionRepository, UserRepository userRepository) {
        this.mentionRepository = mentionRepository;
        this.userRepository = userRepository;
    }
    public List<Mention> processMentions(Tweet tweet) {

        String content = tweet.getContent();

        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(content);

        Set<String> processedUsernames = new HashSet<>();
        List<Mention> mentions = new ArrayList<>();

        while (matcher.find()) {

            String username = matcher.group(1);

            if (processedUsernames.contains(username)) continue;

            User user = userRepository.findByUsername(username);

            if (user != null) {

                Mention mention = new Mention();
                mention.setTweet(tweet);
                mention.setUser(user);

                mentions.add(mention);
                processedUsernames.add(username);
            }
        }

        mentionRepository.saveAll(mentions);

        return mentions;
    }}
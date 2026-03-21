package com.raghav.xclone.mention.service;


import com.raghav.xclone.mention.entity.Mention;
import com.raghav.xclone.mention.repo.MentionRepository;
import com.raghav.xclone.tweet.entity.Tweet;
import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public List<User> findMentionInTweet(Tweet tweet){
        String content = tweet.getContent();
        List<User> mentionList = new ArrayList<>();
        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(content);
        
        java.util.Set<String> processedUsernames = new java.util.HashSet<>();

        while (matcher.find()){
            String username = matcher.group(1);
            
            if (processedUsernames.contains(username)) {
                continue;
            }

            User user = userRepository.findByUsername(username);
            if (user != null) {
                Mention mention = new Mention();
                mention.setTweet(tweet);
                mention.setUser(user);
                mentionRepository.save(mention);
                
                mentionList.add(user);
                processedUsernames.add(username);
            }
        }
        return mentionList;
    }
}

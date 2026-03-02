package com.raghav.xclone.user.service;


import com.raghav.xclone.user.entity.User;
import com.raghav.xclone.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User users = repo.findByUsername(username);
        if (users == null){
            throw new UsernameNotFoundException("User Not Found");

        }
        return new UserPrinciple(users);
    }
}

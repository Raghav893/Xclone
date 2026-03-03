package com.raghav.xclone.user.repo;

import com.raghav.xclone.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

   Page <User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}

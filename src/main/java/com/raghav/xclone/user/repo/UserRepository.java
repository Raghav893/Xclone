package com.raghav.xclone.user.repo;

import com.raghav.xclone.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

   Page <User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}

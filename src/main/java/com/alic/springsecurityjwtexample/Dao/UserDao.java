package com.alic.springsecurityjwtexample.Dao;

import com.alic.springsecurityjwtexample.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}

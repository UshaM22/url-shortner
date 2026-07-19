package com.urlshortener.repository;

import com.urlshortener.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserDetailRepository extends JpaRepository<User, Long> {


    Optional<User> findByUserName(String userName);

    Boolean existsByUserName(String userName);
}


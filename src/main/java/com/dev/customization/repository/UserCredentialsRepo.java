package com.dev.customization.repository;

import com.dev.customization.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepo extends JpaRepository<UserCredentials, Long> {
    UserCredentials findByEmail(String findByEmail);
}
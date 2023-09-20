package com.waqhussein.springsecuritylayer.repository;

import com.waqhussein.springsecuritylayer.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, String> {
    UserDetailsEntity findByEmailAddress(String emailAddress);
}

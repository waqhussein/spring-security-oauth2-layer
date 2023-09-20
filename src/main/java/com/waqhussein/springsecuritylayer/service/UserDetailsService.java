package com.waqhussein.springsecuritylayer.service;

import com.waqhussein.springsecuritylayer.entity.UserDetailsEntity;
import com.waqhussein.springsecuritylayer.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public void saveUser(UserDetailsEntity userDetailsEntity){
        userDetailsRepository.save(userDetailsEntity);
    }
    public UserDetailsEntity getUser(String email){
        return userDetailsRepository.findByEmailAddress(email);
    }
}

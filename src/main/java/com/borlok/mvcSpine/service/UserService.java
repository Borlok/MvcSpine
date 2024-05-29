package com.borlok.mvcSpine.service;

import com.borlok.mvcSpine.model.User;
import com.borlok.mvcSpine.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author Erofeevskiy Yuriy on 28.05.2024
 */

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }
}

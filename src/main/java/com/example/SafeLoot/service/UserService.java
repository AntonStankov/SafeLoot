package com.example.SafeLoot.service;

import com.example.SafeLoot.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User findByEmail(String email) throws Exception;
    String generateOtp(User user);
    
}

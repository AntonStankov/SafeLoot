package com.example.SafeLoot.service;

import com.example.SafeLoot.entity.User;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public interface UserService {
    public User findByEmail(String email) throws Exception;
//    String generateOtp(User user);
    boolean checkPassword(String password) throws IOException;
    int checkStrength(String password);
}

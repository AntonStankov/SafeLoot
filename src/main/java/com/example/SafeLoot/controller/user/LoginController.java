package com.example.SafeLoot.controller.user;



import com.example.SafeLoot.entity.User;
import com.example.SafeLoot.jwt.JwtTokenService;
import com.example.SafeLoot.service.UserRepository;
import com.example.SafeLoot.service.UserService;
import com.example.SafeLoot.userDetails.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class LoginController {



    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private CustomUserDetailsService detailsService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody User user) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        authenticate(user.getEmail(), user.getPassword());
        User newUser = userService.findByEmail(user.getEmail());
        if(newUser.getIsBanned() == Boolean.TRUE) throw new Exception("Your account has been banned!");
        System.out.println(newUser);
        if (newUser.getEmail().equals(user.getEmail()) && passwordEncoder.matches(user.getPassword(), newUser.getPassword())) {
            String token = jwtTokenService.generateToken(user.getEmail());
            String email = jwtTokenService.getEmailFromToken(token);
            System.out.println(email);
//            jwtTokenService.validateToken(token);
            newUser.setLastLogin(LocalDateTime.now());
            userRepository.save(newUser);
            return new LoginResponse("Login successful", token);
        } else {
            return new LoginResponse("Login failed", null);
        }
    }
}

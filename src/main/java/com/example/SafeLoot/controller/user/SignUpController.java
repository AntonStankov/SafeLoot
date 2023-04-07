package com.example.SafeLoot.controller.user;



import com.example.SafeLoot.entity.Role;
import com.example.SafeLoot.entity.User;
import com.example.SafeLoot.entity.helpClasses.Roles;
import com.example.SafeLoot.service.RoleRepo;
import com.example.SafeLoot.service.UserRepository;
import com.example.SafeLoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class SignUpController {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;
    @PostMapping("/signup")
    public User processRegister(@RequestBody User user) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        if (userService.checkPassword(user.getPassword())){
//
//        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setIsBanned(Boolean.FALSE);
        user.setOtp(0);
        List<Role> roles = new ArrayList<>();
//        roles.add(roleRepo.findByRoleName(Roles.USER).get(0));
        user.setRoles(roles);
        userRepo.save(user);
        return user;
    }
}

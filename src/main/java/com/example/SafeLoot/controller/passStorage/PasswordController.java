package com.example.SafeLoot.controller.passStorage;


import com.example.SafeLoot.entity.PasswordStorage;
import com.example.SafeLoot.entity.User;
import com.example.SafeLoot.service.UserService;
import com.example.SafeLoot.service.passwordStorage.PasswordRepo;
import com.example.SafeLoot.service.passwordStorage.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://locahost:8085")
@RequestMapping("/passStorage")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordRepo passwordRepo;
    @PostMapping("/save")
    public PasswordStorage save(@RequestBody PasswordRequest passwordStorage) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User userContext =  userService.findByEmail(principal.getUsername());
        if (passwordStorage.getPassword().length() < 1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty value of password is not permitted");
        if (passwordStorage.getPassName().isEmpty()) throw  new NullPointerException("Empty value of password name is not permitted");
        return passwordService.save(passwordStorage.getPassName(), passwordStorage.getPassword(), passwordStorage.getUrl(), userContext);
    }

    @PostMapping("/decrPass")
    public String decrPass(@RequestBody DecrPassRequest passwordRequest) throws Exception {//TODO: can not accept Long in RequestBody
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User userContext =  userService.findByEmail(principal.getUsername());

        if (passwordEncoder.matches(passwordRequest.getUserPassword(), userContext.getPassword())){
            PasswordStorage passwordStorage = passwordRepo.findById(passwordRequest.getId()).orElseThrow(Exception::new);
            return passwordService.decryptPass(passwordStorage.getPasswordEncr());
        }
        else throw new Exception("Wrong password!");

    }


    @GetMapping("/myPass")
    public List<PasswordStorage> findMyPasses() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User userContext =  userService.findByEmail(principal.getUsername());

        return userContext.getPasswords();
    }


    @CrossOrigin
    @PostMapping("/generatePass")
    public String generatePass(@RequestBody Length length){
        return passwordService.generatePassword(length.getLength());
    }

}

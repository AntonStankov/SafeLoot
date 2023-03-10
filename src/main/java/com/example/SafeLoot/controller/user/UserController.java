package com.example.SafeLoot.controller.user;



import com.example.SafeLoot.Id;
import com.example.SafeLoot.entity.Role;
import com.example.SafeLoot.entity.User;
import com.example.SafeLoot.entity.helpClasses.Roles;
import com.example.SafeLoot.service.RoleRepo;
import com.example.SafeLoot.service.UserRepository;
import com.example.SafeLoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepo roleRepo;

    @GetMapping("/findAll")
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @PostMapping("/ban")
    public Boolean banUser(@RequestBody Id id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User user =  userService.findByEmail(principal.getUsername());
        Boolean permit = Boolean.FALSE;
        for (Role role : user.getRoles()){
            if (role.getRoleName() == Roles.SUPER_ADMIN) {
                permit = Boolean.TRUE;
                break;
            }
        }

        if (permit == Boolean.TRUE){
            User userToBan = userRepository.findById(id.get_id()).orElseThrow(Exception ::new);
            userToBan.setIsBanned(Boolean.TRUE);
            userRepository.save(user);
            return Boolean.TRUE;
        }
        else{
            throw new Exception("You can't ban, because you don't have rights!");
        }

    }

    @PostMapping("/setRole")
    public User setRole(@RequestBody Id id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User userContext =  userService.findByEmail(principal.getUsername());

        Boolean permit = Boolean.FALSE;
        for (Role role : userContext.getRoles()){
            if (role.getRoleName() == Roles.SUPER_ADMIN) {
                permit = Boolean.TRUE;
                break;
            }
        }

        if (permit == Boolean.TRUE) {
            User user = userRepository.findById(id.get_id()).orElseThrow(Exception::new);
            Role role = roleRepo.findById(id.getRole_id()).orElseThrow(Exception::new);
            List<Role> roles = user.getRoles();
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
            return user;
        }
        else throw new Exception("You are not able to give someone permissions!");

    }


    @GetMapping("/userContext")
    public String getUserFromContext() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User userContext =  userService.findByEmail(principal.getUsername());

        String username = userContext.getFirstName() + " " + userContext.getLastName();
        return username;
    }
}

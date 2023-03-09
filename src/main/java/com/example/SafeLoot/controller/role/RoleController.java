package com.example.SafeLoot.controller.role;


import com.example.SafeLoot.entity.Role;
import com.example.SafeLoot.service.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepo roleRepo;
    @PostMapping("/save")
    public Role save(@RequestBody Role role){
        return roleRepo.save(role);
    }
}

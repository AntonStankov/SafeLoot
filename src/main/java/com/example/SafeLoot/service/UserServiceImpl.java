package com.example.SafeLoot.service;



import com.example.SafeLoot.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public User findByEmail(String email) throws Exception {
        Query query = entityManager.createNamedQuery("User.findByEmail").setParameter("email", email);
        List<User> userList = query.getResultList();
        if (userList.isEmpty()){
            throw  new Exception("There is no user with email: " + email);
        }
        else {
            return userList.get(0);
        }
    }
}

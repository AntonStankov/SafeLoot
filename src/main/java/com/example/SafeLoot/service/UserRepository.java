package com.example.SafeLoot.service;



import com.example.SafeLoot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email=:email")
    List<User> findByEmail(@Param("email") String email);
}

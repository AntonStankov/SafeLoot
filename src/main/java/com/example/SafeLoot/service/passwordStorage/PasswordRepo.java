package com.example.SafeLoot.service.passwordStorage;


import com.example.SafeLoot.entity.PasswordStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepo extends JpaRepository<PasswordStorage, Long> {

}

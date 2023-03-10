package com.example.SafeLoot.service.passwordStorage;


import com.example.SafeLoot.entity.PasswordStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PasswordRepo extends JpaRepository<PasswordStorage, Long> {


}

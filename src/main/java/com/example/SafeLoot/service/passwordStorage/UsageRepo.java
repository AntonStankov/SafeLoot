package com.example.SafeLoot.service.passwordStorage;

import com.example.SafeLoot.entity.helpClasses.Usage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsageRepo extends JpaRepository<Usage, Long> {
}

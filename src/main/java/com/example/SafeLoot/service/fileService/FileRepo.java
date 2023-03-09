package com.example.SafeLoot.service.fileService;

import com.example.SafeLoot.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;



@Service
public interface FileRepo extends JpaRepository<FileStorage, Long> {
}

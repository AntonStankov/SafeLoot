package com.example.SafeLoot.service.fileService;

import com.example.SafeLoot.entity.FileStorage;
import com.example.SafeLoot.entity.PasswordStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface FileRepo extends JpaRepository<FileStorage, Long> {
    @Query("select u from FileStorage u where u.fileName=:fileName")
    List<FileStorage> findByFileName(@Param("fileName") String fileName);
}

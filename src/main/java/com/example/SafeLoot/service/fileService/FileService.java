package com.example.SafeLoot.service.fileService;


import com.example.SafeLoot.entity.FileStorage;
import org.springframework.stereotype.Service;


@Service
public interface FileService {
    FileStorage saveFile(FileStorage fileStorage);
}

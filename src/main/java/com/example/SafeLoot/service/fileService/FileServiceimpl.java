package com.example.SafeLoot.service.fileService;


import com.example.SafeLoot.entity.FileStorage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FileServiceimpl implements FileService{

    @Autowired
    private FileRepo fileRepo;

    @Transactional
    @Override
    public FileStorage saveFile(FileStorage fileStorage) {
        return fileRepo.save(fileStorage);
    }
}

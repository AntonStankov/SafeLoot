package com.example.SafeLoot.controller.filesController;



import com.example.SafeLoot.entity.FileStorage;
import com.example.SafeLoot.entity.User;
import com.example.SafeLoot.service.UserService;
import com.example.SafeLoot.service.fileService.FileRepo;
import com.example.SafeLoot.service.fileService.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FilesController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private FileService fileService;


    @PostMapping("/saveFile")
    public FileStorage save(@RequestParam MultipartFile file) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User userContext =  userService.findByEmail(principal.getUsername());

        FileStorage fileStorage = new FileStorage();
        fileStorage.setFileName(file.getOriginalFilename());
        fileStorage.setFileType(fileStorage.getFileName().substring(fileStorage.getFileName().lastIndexOf(".") + 1));
        fileStorage.setUser(userContext);
        fileStorage.setFile_content(file.getBytes());
        fileStorage.setSize(file.getSize());
        System.out.println(Arrays.toString(file.getBytes()));

        return fileService.saveFile(fileStorage);
        //TODO: display custom message
    }

    @GetMapping("/findAll")
    public List<FileStorage> findAll(){
        return fileRepo.findAll();
    }
}

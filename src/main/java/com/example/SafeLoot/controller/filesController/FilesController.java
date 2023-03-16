package com.example.SafeLoot.controller.filesController;



import com.example.SafeLoot.entity.FileStorage;
import com.example.SafeLoot.entity.User;
import com.example.SafeLoot.service.UserService;
import com.example.SafeLoot.service.fileService.FileRepo;
import com.example.SafeLoot.service.fileService.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
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

    SecureRandom secureRandom = new SecureRandom();
    byte[] keyBytes = secureRandom.generateSeed(16);
    String secretKeyString = Base64.getEncoder().encodeToString(keyBytes);
    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
    byte[] hashBytes = sha256.digest(secretKeyString.getBytes(StandardCharsets.UTF_8));
    SecretKeySpec secretKey = new SecretKeySpec(hashBytes, "AES");

    public FilesController() throws NoSuchAlgorithmException {
    }


    @PostMapping("/saveFile")
    public FileStorage save(@RequestParam MultipartFile file) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User userContext =  userService.findByEmail(principal.getUsername());

        FileStorage fileStorage = new FileStorage();
        fileStorage.setFileName(file.getOriginalFilename());
        fileStorage.setFileType(fileStorage.getFileName().substring(fileStorage.getFileName().lastIndexOf(".") + 1));
        fileStorage.setUser(userContext);

        byte[] fileBytes = file.getBytes();

        SecretKeySpec keySpec = new SecretKeySpec(hashBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encryptedBytes = cipher.doFinal(fileBytes);
        fileStorage.setFile_content(encryptedBytes);

        fileStorage.setSize(file.getSize());
        System.out.println(file.getBytes());

        return fileService.saveFile(fileStorage);
    }

    @GetMapping("/findAll")
    public List<FileStorage> findAll(){
        return fileRepo.findAll();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws Exception {
        FileStorage fileBytes = fileRepo.findById(id).orElseThrow(Exception::new);
        SecretKeySpec keySpec = new SecretKeySpec(hashBytes, "AES");


        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);


        byte[] decryptedBytes = cipher.doFinal(fileBytes.getFile_content());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(decryptedBytes.length);
            headers.setContentDisposition(ContentDisposition.attachment().filename(fileBytes.getFileName()).build());

            return new ResponseEntity<>(decryptedBytes, headers, HttpStatus.OK);

    }


    @GetMapping("/findMyFiles")
    public List<FileStorage> findMyFiles() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User userContext =  userService.findByEmail(principal.getUsername());

        return userContext.getFiles();
    }
}

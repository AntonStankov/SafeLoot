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

//        byte[] fileBytes = Files.readAllBytes(file);

        FileStorage fileStorage = new FileStorage();
        fileStorage.setFileName(file.getOriginalFilename());
        fileStorage.setFileType(fileStorage.getFileName().substring(fileStorage.getFileName().lastIndexOf(".") + 1));
        fileStorage.setUser(userContext);

//        byte[] fileBytes = file.getBytes();
//        PGobject pgObject = new PGobject();
//        pgObject.setType("bytea");
//        pgObject.setValue(new String(fileBytes, StandardCharsets.ISO_8859_1));

        fileStorage.setFile_content(file.getBytes());


        fileStorage.setSize(file.getSize());
        System.out.println(file.getBytes());

        return fileService.saveFile(fileStorage);
        //TODO: display custom message
    }

    @GetMapping("/findAll")
    public List<FileStorage> findAll(){
        return fileRepo.findAll();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws Exception {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails principal = (UserDetails) authentication.getPrincipal();
//        User userContext =  userService.findByEmail(principal.getUsername());
//
//
//        // Load file into a byte array
//        byte[] fileBytes = loadFileIntoByteArray();
        FileStorage fileBytes = fileRepo.findById(id).orElseThrow(Exception::new);
//        Boolean passed = Boolean.FALSE;
//        for (FileStorage fileStorage : userContext.getFiles()){
//            if (fileStorage.getFile_content() == fileBytes.getFile_content()) {
//                passed = Boolean.TRUE;
//                break;
//            }
//        }
//        if (passed){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(fileBytes.getFile_content().length);
            headers.setContentDisposition(ContentDisposition.attachment().filename(fileBytes.getFileName()).build());

            return new ResponseEntity<>(fileBytes.getFile_content(), headers, HttpStatus.OK);
//        }else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is not your file");

    }


    @GetMapping("/findMyFiles")
    public List<FileStorage> findMyFiles() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User userContext =  userService.findByEmail(principal.getUsername());

        return userContext.getFiles();
    }
}

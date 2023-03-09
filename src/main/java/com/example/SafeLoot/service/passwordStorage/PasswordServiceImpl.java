package com.example.SafeLoot.service.passwordStorage;


import com.example.SafeLoot.entity.PasswordStorage;
import com.example.SafeLoot.entity.User;
import com.example.SafeLoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class PasswordServiceImpl implements PasswordService{

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    String secretKey = "mysecretkey12345";
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");

    @Autowired
    private PasswordRepo passwordRepo;
    @Override
    public PasswordStorage save(String passwordName, String password, String url, User user) throws Exception {



        PasswordStorage passwordStorage = new PasswordStorage();
        passwordStorage.setPasswordName(passwordName);
//        passwordStorage.setPasswordEncr(passwordEncoder.encode(password));


        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encrypted = cipher.doFinal(password.getBytes());
        String encodedEncrypted = Base64.getEncoder().encodeToString(encrypted);


        passwordStorage.setPasswordEncr(encodedEncrypted);
        passwordStorage.setUrl(new URL(url));
        passwordStorage.setUser(user);

        passwordRepo.save(passwordStorage);
        return passwordStorage;
    }

    public String decryptPass(String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decodedEncrypted = Base64.getDecoder().decode(password);
        byte[] decrypted = cipher.doFinal(decodedEncrypted);
        return new String(decrypted);
    }

    @Override
    public String generatePassword(int length) {
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String upperCaseLetters = lowerCaseLetters.toUpperCase();
        String numbers = "1234567890";
        String specialSigns = "@#$%^&*&/=~";

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++){
            int random_int = (int)Math.floor(Math.random() * (4 - 1 + 1) + 1);
            switch (random_int) {
                case 1 -> password.append(lowerCaseLetters.charAt((int) Math.floor(Math.random() * (36 - 1 + 1) + 1)));
                case 2 -> password.append(upperCaseLetters.charAt((int) Math.floor(Math.random() * (36 - 1 + 1) + 1)));
                case 3 -> password.append(numbers.charAt((int) Math.floor(Math.random() * (10 - 1 + 1) + 1)));
                case 4 -> password.append(specialSigns.charAt((int) Math.floor(Math.random() * (11 - 1 + 1) + 1)));
            }
            System.out.println(password.toString());
        }
        return password.toString();
    }
}

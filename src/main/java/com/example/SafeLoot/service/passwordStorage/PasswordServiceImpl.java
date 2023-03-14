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
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class PasswordServiceImpl implements PasswordService{

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    String secretKey = "mysecretkey12345";

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String ENCRYPTION_KEY = "eb283445026f14a97f3a83a4a4090f2f";

    private static final SecureRandom random = new SecureRandom();
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

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            Key secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedPasswordBytes = cipher.doFinal(Base64.getDecoder().decode(password));
            return new String(decryptedPasswordBytes);

    }



    @Override
    public String generatePassword(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("password length must be at least 1");
        }

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(PASSWORD_ALLOW_BASE.length());
            password.append(PASSWORD_ALLOW_BASE.charAt(randomIndex));
        }
        return password.toString();
    }

    @Override
    public String encryptPassword(String password) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            Key secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedPasswordBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedPasswordBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }


}

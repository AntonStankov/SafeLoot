package com.example.SafeLoot.service.passwordStorage;

import com.example.SafeLoot.entity.PasswordStorage;
import com.example.SafeLoot.entity.User;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public interface PasswordService {
    PasswordStorage save(String passwordName, String password, String url, User user) throws Exception;

    String decryptPass(String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, MalformedURLException;

    String generatePassword(int length);
}

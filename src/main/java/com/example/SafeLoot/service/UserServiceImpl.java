package com.example.SafeLoot.service;



import com.example.SafeLoot.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;





    @Override
    public User findByEmail(String email) throws Exception {
        Query query = entityManager.createNamedQuery("User.findByEmail").setParameter("email", email);
        List<User> userList = query.getResultList();
        if (userList.isEmpty()){
            throw  new Exception("There is no user with email: " + email);
        }
        else {
            return userList.get(0);
        }
    }


    @Override
    public boolean checkPassword(String password) throws IOException {


        BufferedReader reader = new BufferedReader(new FileReader("rockyou1.txt"));
        BufferedReader reader2 = new BufferedReader(new FileReader("rockyou2.txt"));
        BufferedReader reader3 = new BufferedReader(new FileReader("common_passwords.txt"));
        BufferedReader reader4 = new BufferedReader(new FileReader("top_200_passwords.txt"));

        String line;

        //file 1
        while ((line = reader.readLine()) != null) {
            if (password.equals(line)) {
                reader.close();
                return false;
            }
        }
        //file 2
        while ((line = reader2.readLine()) != null) {
            if (password.equals(line)) {
                reader.close();
                return false;
            }
        }
        //file 3
        while ((line = reader3.readLine()) != null) {
            if (password.equals(line)) {
                reader.close();
                return false;
            }
        }
        //file 4
        while ((line = reader4.readLine()) != null) {
            if (password.equals(line)) {
                reader.close();
                return false;
            }
        }

        reader.close();
        return true;
    }



    @Override
    public int checkStrength(String password) {
        int score = 0;
        if (password.length() >= 8) {
            score++;
        }
        if (password.matches("(?=.*[a-z]).*")) {
            score++;
        }
        if (password.matches("(?=.*[A-Z]).*")) {
            score++;
        }
        if (password.matches("(?=.*[0-9]).*")) {
            score++;
        }
        if (password.matches("(?=.*[!@#$%^&*()_+\\-={};':\"\\\\|,.<>\\/?]).*")) {
            score++;
        }
        return score;

    }


}

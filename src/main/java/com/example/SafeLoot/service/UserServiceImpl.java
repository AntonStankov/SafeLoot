package com.example.SafeLoot.service;



import com.example.SafeLoot.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

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
    public String generateOtp(User user){
        try {
            int randoPin = (int) (Math.random() * 9000) + 1000;
            user.setOtp(randoPin);
            userRepository.save(user);




//            Properties properties=new Properties();
////fill all the information like host name etc.
//            Session session=Session.getInstance(properties,null);
//            MimeMessage message=new MimeMessage(session);
//            message.setFrom(new InternetAddress("antonstankov007@gmail.com"));
//            message.addRecipient(Message.RecipientType.TO,
//                    new InternetAddress(user.getEmail()));
////            message.setHeader();
//            message.setText("Hi, This mail is to inform you..." + randoPin);
//            Transport.send(message);

            return "success";

        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}

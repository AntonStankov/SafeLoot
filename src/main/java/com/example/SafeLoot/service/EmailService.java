package com.example.SafeLoot.service;

import com.example.SafeLoot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

    public String generateOtp(User user){
        try {
            int randomPin = (int) (Math.random() * 9000) + 1000;
            user.setOtp(randomPin);
            userRepository.save(user);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("anton.m.stankov.2021@elsys-bg.org");
            message.setTo(user.getEmail());
            message.setSubject("Your Two Factor Auth Code Is Here");
            message.setText("Auth Code: " + randomPin);
            javaMailSender.send(message);




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
//yqeigqmqxhfqufff
            return "success";

        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}

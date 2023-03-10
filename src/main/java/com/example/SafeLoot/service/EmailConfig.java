package com.example.SafeLoot.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    private final String username;
    private final String password;
    private final String host;
    private final int port;
    private final boolean tlsEnabled;
    private final boolean authEnabled;

    public EmailConfig(
            @Value("${spring.mail.username}") String username,
            @Value("${spring.mail.password}") String password,
            @Value("${spring.mail.host}") String host,
            @Value("${spring.mail.port}") int port,
            @Value("${spring.mail.properties.mail.smtp.starttls.enable}") boolean tlsEnabled,
            @Value("${spring.mail.properties.mail.smtp.auth}") boolean authEnabled) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.tlsEnabled = tlsEnabled;
        this.authEnabled = authEnabled;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", tlsEnabled);
        props.put("mail.smtp.auth", authEnabled);

        return mailSender;
    }
}

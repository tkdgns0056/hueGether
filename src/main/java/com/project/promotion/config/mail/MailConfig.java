package com.project.promotion.config.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailService(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.naver.com"); //메인 도메인 서버 주소
        javaMailSender.setUsername("tkdgns0056");
        javaMailSender.setPassword("park0407!@");

        javaMailSender.setPort(465); //메일 인증서버 포트
        javaMailSender.setJavaMailProperties(getMailProperties()); //메일 인증서버 정보 가져오기

        return javaMailSender;
    }

    private Properties getMailProperties(){
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol" ,"smtp"); //포로토콜 설정
        properties.setProperty("mail.smtp.auth", "true"); //smtp 인증
        properties.setProperty("mail.smtp.starttls.enable", "true"); // smtp starttls 사용
        //properties.setProperty("mail.debug", "true"); //디버그 사용
        properties.setProperty("mail.smtp.ssl.trust", "smtp.naver.com"); //ssl 인증 서버는 네이버
        properties.setProperty("mail.smtp.ssl.enable", "true"); //ssl 사용

        return properties;
    }
}

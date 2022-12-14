package com.project.promotion.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender; //Bean 등록해둔 MailConfig를 가져온다.
    private String ePw; //인증번호

    //메일 내용 작성
    private MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {


        MimeMessage message = javaMailSender.createMimeMessage();

        System.out.println("보내는 대상 : " + to);
        System.out.println("인증 번호 : " + ePw);

        message.addRecipients(Message.RecipientType.TO, to); //보내는 대상
        message.setSubject("회원가입 이메일 인증"); //제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요 </h1>";
        msgg += "<h> 휴게더 입니다.<h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주시길 바랍니다. </p>";
        msgg += "<br>";
        msgg += "<div align='center style ='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE: <strong>";
        msgg += ePw + "</strong><div><br/> "; //메일에 인증번호 넣기
        msgg += "</div>";

        message.setText(msgg, "utf-8", "html"); //내용, charset타입, subtype

        //보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("tkdgns0056@naver.com", "admin"));
        System.out.println("msgg = " + msgg);

        return message;
    }


    //랜덤 인증 코드 전송
    public String createKey(){
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for(int i= 0; i< 8; i++) {
            int index = rnd.nextInt(3);

            switch (index){
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    //0~9
                    break;
            }
        }

        return key.toString();
    }

    /**
     * 메일 발송
     * sendSimpleMessage의 매개변수로 들어온 to는 곧 이메일 주소가 된다.
     * MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
     * 그리고 bean으로 등록해둔 javaMail 객체를 사용해서 이메일을 send 한다.
     */
    public String sendSimpleMessage(String to) throws Exception{

        ePw = createKey(); //랜덤 인증번호 생성

        MimeMessage message = createMessage(to); //메일 발송

        try{
            javaMailSender.send(message);
        } catch (MailException es){
            es.printStackTrace();
            throw  new IllegalArgumentException();
        }

        return ePw; //메일로 보냈던 인증 코드를 서버로 반환.
    }
}

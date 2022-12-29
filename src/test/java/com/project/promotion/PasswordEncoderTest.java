package com.project.promotion;

import com.project.promotion.model.member.Member;
import com.project.promotion.service.auth.PrincipalDetailsService;
import com.project.promotion.service.member.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private PrincipalDetailsService principalDetailsService;

    @Test
    @DisplayName("패스워드 암호화 테스트 및 회원가입")
    void passwordEncodeTest(){
        String email = "user@naver.com";
        String rawPw = "park0056";

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(rawPw);

        userService.checkLoginInsert(member);

        String encodedPassword = principalDetailsService.loadUserByUsername(email).getPassword();

        assertAll(
                () -> assertNotEquals(rawPw, encodedPassword),
                () -> assertTrue(passwordEncoder.matches(rawPw, encodedPassword))
        );
    }

}

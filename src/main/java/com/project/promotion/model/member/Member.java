package com.project.promotion.model.member;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member{

    private Integer userId; //유저 시퀀스
    private String loginType;
    private String userName; //유저 이름
    private String password; //유저 패스워드
    private String email; //유저 이메일
    private String address; //유저 주소
    private int phoneNumber; //유저 핸드폰 번호

    private String intgrUserId; //통합 유저 아이디
    private Date intgrDt; //통합 일시


    /*
      소셜 로그인 전용 빌더
     */
    @Builder
    public Member (Integer userId, String userName, String email, String loginType){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.loginType = loginType;
    }
}

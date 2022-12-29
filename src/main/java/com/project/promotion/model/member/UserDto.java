package com.project.promotion.model.member;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * validation 처리를 하기 위한 UserDtO
 */
@Data
public class UserDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 2, max = 10)
    private String userName;
    @NotBlank
    private String password;
//    @NotBlank
//    private String pwConfirm; //비밀번호 확인

    private String loginType;


    /*
       유효성 검사를 위한 빌더 패턴
     */
    public Member toEntity(){
        return Member.builder()
                .userName(userName)
                .password(password)
                .email(email)
                .build();
    }

}

package com.project.promotion.model.login;

import com.project.promotion.model.member.Member;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 로그인 validation을 위한 DTO
 */
@Data
public class LoginUserDto {

    @NotBlank(message = "이메일을 입력해 주시기 바랍니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주시기 바랍니다.")
    private String password;

    private String loginType;

    /*
     유효성 검사를 위한 빌드 패턴
     */
    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .loginType(loginType)
                .build();
    }
}

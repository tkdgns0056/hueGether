package com.project.promotion.model.auth;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class MemberAuthority {

    private int userId; //유저 시퀀스
    private String authId; //권한 시퀀스

    //Social 유저 권한 저장 하기 위한 빌더

}

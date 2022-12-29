package com.project.promotion.model.login;

import lombok.Getter;

@Getter
public enum LoginType {

    NORMAL("자사가입"),
    SOCIAL("소셜가입");

    private String loginTypeKey;

   LoginType(String loginTypeKey){
        this.loginTypeKey = loginTypeKey;
    }
}

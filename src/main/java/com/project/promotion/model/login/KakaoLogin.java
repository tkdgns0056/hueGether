package com.project.promotion.model.login;

import java.util.HashMap;
import java.util.Map;

public class KakaoLogin implements SocialLogin {

    private Map<String, Object> attributes;
    private Map<String, Object> attributesProperties;
    private Map<String, Object> attributesAccount;
    private Map<String, Object> attributesProfile;

    public KakaoLogin(Map<String, Object> attributes){

        this.attributes = attributes;
        this.attributesProperties = (Map<String, Object>) attributes.get("properties");
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.attributesProfile = (Map<String, Object>) attributesAccount.get("profile");
    }


    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {

        return "kakao";
    }

    @Override
    public String getEmail() {

        return (String) attributesAccount.get("email");
    }

    @Override
    public String getNickName() {

        return (String) attributesProperties.get("nickname");
    }
}

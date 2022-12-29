package com.project.promotion.model.login;

import java.util.Map;

public class NaverLogin implements SocialLogin {

    @Override
    public String getProviderId() {
        return null;
    }

    public NaverLogin(Map<String, Object> attributes) {
    }
    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getNickName() {
        return null;
    }

    @Override
    public String getProvider() {
        return null;
    }
}

package com.project.promotion.model.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Authority implements GrantedAuthority {

    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반유저"),
    SELLER("ROLE_SELLER", "판매자");


    private String roleId;
    private String roleName;

    Authority(String roleId, String roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }


    @Override
    public String getAuthority() {
        return this.roleId;
    }
}

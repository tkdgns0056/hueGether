package com.project.promotion.model.auth;

import com.project.promotion.model.login.OAuthAttributes;
import com.project.promotion.model.login.SocialInfo;
import com.project.promotion.model.member.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
@NoArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User{

    private Member member;
    private MemberAuthority memberAuthority;
    private SocialInfo socialInfo;
    private Map<String, Object> attributes;

    private OAuthAttributes oAuthAttributes;


    //일반 유저 로그인 시 사용하는 생성자
    public PrincipalDetails(Member member){

        this.member = member;
    }

    // OAuth2User를 사용한 SNS 유저 로그인 시 사용한는 생성자
    public PrincipalDetails(Member member, MemberAuthority authority, SocialInfo socialInfo, Map<String, Object> attributes){
        this.member = member;
        this.memberAuthority = authority;
        this.socialInfo = socialInfo;
        this.attributes = attributes;
    }

    /* 해당 유저의 권한을 리턴하는 곳 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> role = new ArrayList<>();

        role.add((GrantedAuthority) () -> memberAuthority.getAuthId());
        return role;
    }

    @Override
    public String getPassword() {

        return member.getPassword();
    }

    @Override
    public String getUsername() {

        return String.valueOf(member.getUserId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    /* 소셜 로그인 관련 */

    // Oauth2User 즉, 소셜 로그인 유저으 oauth2user 의 정보를 확인하기 위한 메서드
    @Override
    public Map<String, Object> getAttributes() {

        return attributes;
    }
    @Override
    public String getName() {

       return String.valueOf(this.member.getUserId());
    }

}

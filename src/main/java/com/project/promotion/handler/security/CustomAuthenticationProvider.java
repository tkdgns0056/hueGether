package com.project.promotion.handler.security;

import com.project.promotion.mapper.UserMapper;
import com.project.promotion.model.auth.MemberAuthority;
import com.project.promotion.model.auth.PrincipalDetails;
import com.project.promotion.model.login.LoginType;
import com.project.promotion.model.member.Member;
import com.project.promotion.service.auth.PrincipalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("unchecked")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PrincipalDetailsService principalDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //유저 이메일
        String userEmail = authentication.getName();
        //비밀번호
        String password = (String) authentication.getCredentials();


        PrincipalDetails member = (PrincipalDetails) principalDetailsService.loadUserByUsername(userEmail);


        if(member == null){
            throw new InternalAuthenticationServiceException("존재하지 않은 회원입니다.");
        }

        //입려한 비밀번호가 암호화된 비밀번호와 같지 않으면
        if (!passwordEncoder.matches(password, member.getPassword())) {
                throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        // 입력한 비밀번호가 현재 비밀번호와 같으면
        else {
            //권한 조회
            MemberAuthority getMemberAuthorities = userMapper.findMemberAuthorities(userEmail , LoginType.NORMAL.getLoginTypeKey());
            System.out.println("getMemberAuthorities.getAuthId() = " + getMemberAuthorities.getAuthId());

            //권한이 있을 경우
            if(getMemberAuthorities != null) {

                MemberAuthority authority = new MemberAuthority();

                authority.setUserId(getMemberAuthorities.getUserId());
                authority.setAuthId(getMemberAuthorities.getAuthId());
                System.out.println("authority UserId = " + authority.getUserId());
                System.out.println("authority AuthId = " + authority.getAuthId());

                member.setMemberAuthority(authority);
            }
        }

             List<GrantedAuthority> authorities = (List<GrantedAuthority>) member.getAuthorities();

            return new UsernamePasswordAuthenticationToken(member, password, authorities);

        }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}


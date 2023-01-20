package com.project.promotion.service.auth;

import com.project.promotion.mapper.UserMapper;
import com.project.promotion.model.login.LoginType;
import com.project.promotion.model.member.Member;
import com.project.promotion.model.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

          String loginType = userMapper.findMemberLoginType(username, LoginType.NORMAL.getLoginTypeKey());

          // email 로 유저 가져오기
          Member member = userMapper.findMemberAuthInfo(username, loginType);

         if(member == null) { //정보 없으면
             throw new UsernameNotFoundException("존재하지 않는 회원입니다.");

         } else if(member.getLoginType().equals(LoginType.NORMAL.getLoginTypeKey())) {

             return new PrincipalDetails(member);
         }

         return null;
    }
}

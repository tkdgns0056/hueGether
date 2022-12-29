package com.project.promotion.service.member;

import com.project.promotion.mapper.UserMapper;
import com.project.promotion.model.auth.Authority;
import com.project.promotion.model.login.LoginType;
import com.project.promotion.model.member.Member;
import com.project.promotion.model.auth.MemberAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class UserService {

        @Autowired
        private UserMapper userMapper;
        @Autowired
        private  PasswordEncoder passwordEncoder;


        /**
         * 회원 가입 로직
         * @param member
         */
        @Transactional
        public void checkLoginInsert(Member member) {

            MemberAuthority authority = new MemberAuthority();

            //패스워드 bcrypt 암호화
            String password = passwordEncoder.encode(member.getPassword());
            member.setPassword(password);

            // 일반 자사 회원 가입 시 로그인 타입은 NORMAL
            member.setLoginType(LoginType.NORMAL.getLoginTypeKey());

            int flag = userMapper.insertMember(member);

            if(flag > 0) {

                // 일반 자사 회언 가입시 무조건 권한은 ROLE_USER 부여
                authority.setAuthId(Authority.USER.getRoleId());
                authority.setUserId(member.getUserId());

                userMapper.insertMemberAuth(authority);
            }

            System.out.println("가입한 유저 정보 = " + member.getUserName() + "," + authority.getAuthId());
        }

         //아이디 중복 체크
        public boolean isUserIdExist(String email, String loginType){
                // 1 -> true , 0 -> false
                // 즉 true 면 아이디가 있는 것이고, false면 아이디가 없는 것.
                boolean userIdDuplicate = userMapper.userIdExist(email, loginType);
                return userIdDuplicate;
        }
}

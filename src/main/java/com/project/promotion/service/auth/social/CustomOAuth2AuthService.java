package com.project.promotion.service.auth.social;

import com.project.promotion.mapper.UserMapper;
import com.project.promotion.model.auth.Authority;
import com.project.promotion.model.auth.MemberAuthority;
import com.project.promotion.model.auth.PrincipalDetails;
import com.project.promotion.model.login.*;
import com.project.promotion.model.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2AuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserMapper userMapper;

    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 카카오 로그인버튼 클릭 -> 카카오 로그인창 -> 로그인을 완료 -> code 를 리턴(Oauth-client 라이브러리) -> AccessToken 요청
        System.out.println("userRequest = " + userRequest.getAccessToken().getTokenValue());

        // userRequest 정보 -> loadUser함수 호출 -> 카카오로부터 회원프로필을 받아준다.
        System.out.println("userRequest ClientRegistration = " + userRequest.getClientRegistration());
        System.out.println("oAuth2User = " + userRequest.getAdditionalParameters());
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());
        System.out.println("oAuth2User.getAuthorities() = " + oAuth2User.getAuthorities());
        System.out.println("oAuth2User.getName() = " + oAuth2User.getName());

        SocialLogin socialLogin = null;

        String socialType = userRequest.getClientRegistration().getRegistrationId();

        if (socialType.equals("kakao")) {
            System.out.println("카카오 로그인 요청");
            socialLogin = new KakaoLogin(oAuth2User.getAttributes());
        } else if (socialType.equals("naver")) {
            System.out.println("네이버 로그인 요청");
            socialLogin = new NaverLogin(oAuth2User.getAttributes());
        } else {
            System.out.println("구글과 페이스북만 지원");
        }

        String providerId = socialLogin.getProviderId();
        String socialId = socialType + "_" + providerId; // SocialInfo 테이블에 들어갈 컬럼 ex) kakao_1234jhsdf
        String email = socialLogin.getEmail();
        String auth = Authority.USER.getAuthority();
        String userName = socialLogin.getNickName();

        //소셜 유저 조회
        Member member = userMapper.findSocialMember(email, LoginType.SOCIAL.getLoginTypeKey(), socialId);
        //소셜 정보 테이블 조회
        SocialInfo socialInfo = userMapper.findUserAndSocialInfo(email);

        MemberAuthority authority = userMapper.findSocialMemberAndAuth(email);

        if (member == null) {
            System.out.println("소셜 로그인 최초 가입");
            member = Member.builder()
                    .userName(userName)
                    .email(email)
                    .loginType(LoginType.SOCIAL.getLoginTypeKey())
                    .build();

            userMapper.insertSNSMember(member);

            System.out.println("소셜 정보 테이블 저장");
            if (socialInfo == null) {
                socialInfo = SocialInfo.builder()
                        .userId(member.getUserId())
                        .socialId(socialId)
                        .socialType(socialType)
                        .build();

                userMapper.insertSocialInfo(socialInfo);

                socialInfo = userMapper.findUserAndSocialInfo(email);

                if(authority == null) {
                    MemberAuthority memberAuthority = new MemberAuthority();

                    memberAuthority.setAuthId(auth);
                    memberAuthority.setUserId(socialInfo.getUserId());

                    userMapper.insertMemberAuth(memberAuthority);

                }
            }

        } else {
            System.out.println("이미 가입되어 있는 아이디");
        }

        //회원가입된 정보를 다시 불러와서 member에 담는다.
        member = userMapper.findSocialMember(email, LoginType.SOCIAL.getLoginTypeKey(), socialId);
        socialInfo = userMapper.findUserAndSocialInfo(email);
        authority = userMapper.findSocialMemberAndAuth(email);

        return new PrincipalDetails(member, authority, socialInfo, oAuth2User.getAttributes());
    }
}

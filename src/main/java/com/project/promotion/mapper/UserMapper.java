package com.project.promotion.mapper;
import com.project.promotion.model.member.Member;
import com.project.promotion.model.auth.MemberAuthority;
import com.project.promotion.model.login.SocialInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Mapper
public interface UserMapper {

    // 사용자 정보 조회(권한 join)
    Member findMemberAuthInfo(String email, String loginType);

    // 소셜 로그인 멤버 권한 조회(join)
    MemberAuthority findSocialMemberAndAuth(String email);

    // 사용자 권한만 조회
    MemberAuthority findMemberAuthorities(String email, String loginType);

    // 사용자 등록
    public int insertMember(Member member);

    // 권한 등록
    public void insertMemberAuth(MemberAuthority memberAuthority);

    // 사용자 중복 체크 booelan 타입
    public boolean userIdExist(@Param("email") String email, @Param("loginType") String loginType);

    // 소셜 로그인 사용자 등록
    public void insertSNSMember(Member member);

    // 소셜 정보 테이블에 등록
    public void insertSocialInfo(SocialInfo socialInfo);

    // 소셜 로그인 유저 정보 조회
    public Member findSocialMember(@Param("email") String email,
                            @Param("loginType") String loginType,
                            @Param("socialId") String socialId);

    // 소셜 정보 테이블 조회(user 테이블과 조인)
    public SocialInfo findUserAndSocialInfo(String email);

    // 유저 로그인 정보 조회
    public String findMemberLoginType(String email, String loginType);

    public Member findMember(String userEmail);

}

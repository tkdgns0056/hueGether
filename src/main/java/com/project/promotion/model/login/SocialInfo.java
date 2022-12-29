package com.project.promotion.model.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.promotion.exception.OAuth2RegistrationException;
import com.project.promotion.model.member.Member;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Setter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialInfo  {
    private Integer userId;
    private String socialId;   // userNameAttributesKey 값 들어가야함.
    private String socialType; // registrationId 값 들어가면 될거같음.


}

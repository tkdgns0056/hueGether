package com.project.promotion.model.login;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

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

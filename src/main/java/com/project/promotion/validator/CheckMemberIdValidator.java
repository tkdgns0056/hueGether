package com.project.promotion.validator;

import com.project.promotion.mapper.UserMapper;
import com.project.promotion.model.member.UserDto;
import com.project.promotion.service.auth.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckMemberIdValidator extends AbstractValidator<UserDto>{

    private final UserMapper userMapper;

    @Override
    protected void doValidate(UserDto userDto, Errors errors) {
        if(userMapper.userIdExist(userDto.toEntity().getEmail(), userDto.toEntity().getLoginType())){
            // 중복인 경우 //
            errors.rejectValue("email", "이메일 중복 오류" ,"이미 사용중인 이메일 입니다.");
        }
    }
}

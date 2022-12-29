package com.project.promotion.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 중복 검사를 위한 Validator 구현 추상 클래스
 */
@Slf4j
public abstract class AbstractValidator<T> implements Validator {

    /* @Validated는 검증기를 실행하라는 어노테이션
       이 어노테이션이 붙으면 아래에 나오는 WebDataBinder 에 등록한 검증기를 찾아서 실행
       그런데 여러 검증기(Validator)를 등록한다면 그 중에  어떤 검증기가 실행되어야 할지 구분이 필요하다.
       이때 supports() 가 사용된다.
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(Object target, Errors errors) {
        try {
            doValidate((T) target, errors); //유효성 검증
        } catch (IllegalStateException e) {
            log.error("중복 검증 에러", e);
            throw e;
        }
    }


    /* 유효성 검증 로직 */
    protected abstract void doValidate(final T UserDto , final Errors errors);

}

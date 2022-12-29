package com.project.promotion.handler;

import com.project.promotion.exception.CustomValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(CustomValidationException.class) //런타임 Exception이 발생하면 모두 이곳으로 낚아챈다.
    public Map<String, String> validationException(CustomValidationException e){

        return e.getErrorMap();
    }
}

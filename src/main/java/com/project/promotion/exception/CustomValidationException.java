package com.project.promotion.exception;

import java.util.Map;

public class CustomValidationException extends RuntimeException{

    //객체를 구분
    private static final long serialVersionUID = 1l;

    private Map<String, String> errorMap;

    public CustomValidationException(String message, Map<String, String> errorMap){
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap(){
        return errorMap;
    }
}

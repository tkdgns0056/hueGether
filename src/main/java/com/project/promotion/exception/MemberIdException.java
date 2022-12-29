package com.project.promotion.exception;

import org.springframework.security.core.AuthenticationException;

public class MemberIdException extends AuthenticationException {

    public MemberIdException(String msg, Throwable cause) {

        super(msg, cause);
    }

    public MemberIdException(String msg) {
        super(msg);
        System.out.println("msg = " + msg);
    }
}

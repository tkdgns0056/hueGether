package com.project.promotion.exception;

import org.springframework.security.core.AuthenticationException;

public class MemberAuthException extends AuthenticationException {
    public MemberAuthException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MemberAuthException(String msg) {
        super(msg);
    }
}

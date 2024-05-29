package com.borlok.mvcSpine.security;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

/**
 * @author Erofeevskiy Yuriy on 28.05.2024
 */

@Getter
public class JwtAuthenticationException extends AuthenticationException {
    private HttpStatus httpStatus;

    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }

    public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}

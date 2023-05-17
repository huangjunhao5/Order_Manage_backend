package com.backend.pojo.exception;

public class TokenExpiredException extends Exception{
    public TokenExpiredException(String message) {
        super(message);
    }
    public TokenExpiredException(){
        super("Token已过期");
    }
}

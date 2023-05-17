package com.backend.pojo.exception;

public class UserException extends Exception{
    public UserException(){
        super("用户信息请求异常");
    }
    public UserException(String message){
        super(message);
    }
}

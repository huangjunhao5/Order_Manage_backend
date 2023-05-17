package com.backend.pojo.exception;

public class ServerException extends Exception{
    public ServerException(){
        super("服务器繁忙，请重试");
    }
    public ServerException(String message){
        super(message);
    }
}

package com.backend.pojo.client;

//import com.backend.pojo.pojo.ProductStore;
import lombok.Data;

@Data
public class Result<t>{
    public static Integer ok = 200;
    public static Integer err = 201;

    public static Integer Unauthorized = 401;

    private t data;
    private String msg;
    private Integer code;

    public static<t> Result ok(t data){
        return new Result(data,Result.ok);
    }

    public static<t> Result err(t data){
        return new Result(data,Result.err);
    }

    public static<t> Result ok(String msg, t data){
        return new Result(data,msg,Result.ok);
    }

    public static Result err(String msg, Object data){
        return new Result(data,msg,Result.err);
    }

    public static Result Unauthorized(){
        return new Result(Result.Unauthorized);
    }

    public Result(Integer code) {
        this.code = code;
    }

    public Result(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public Result(t data, Integer code) {
        this.data = data;
        this.code = code;
    }

    public Result(t data, String msg, Integer code) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }
    public Result(){
        code = Result.err;
    }
}

package com.backend.pojo.exception;

import com.backend.pojo.client.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
@ResponseBody
public class SysExceptionHandler {
    @ExceptionHandler({UserException.class, ServerException.class})

    public Result error(Exception e){
        log.error(e.getMessage());
        return Result.err(e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public Result UnknownError(Exception e){
        log.error(e.getMessage());
        return Result.err(e.getMessage(), null);
    }
}

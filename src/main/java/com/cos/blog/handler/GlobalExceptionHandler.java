package com.cos.blog.handler;

import com.cos.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

//모든 익셉션을 받아들이게 하는 어노테이션
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public ResponseDto<String> handleArgumentException(Exception e){
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}

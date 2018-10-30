package com.musicfire.common.businessException;

import com.musicfire.common.utiles.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            return new Result().fail(businessException.getCode(),businessException.getMsg());
        }else if(e instanceof BindException){
            BindException bindException = (BindException) e;
            List<FieldError> fieldErrors = bindException.getBindingResult().getFieldErrors();
            StringBuffer sb = new StringBuffer();
            fieldErrors.forEach(fieldError ->
                    sb.append(fieldError.getField()+":"+fieldError.getDefaultMessage()+",")
            );
            return new Result().fail(sb.toString());

        }else if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
            StringBuffer sb = new StringBuffer();
            fieldErrors.forEach(fieldError ->
                    sb.append(fieldError.getField()+":"+fieldError.getDefaultMessage()+",")
            );
            return new Result().fail(sb.toString());
        } else {
            logger.error("【系统异常】{}", e);
            return new Result().fail("未知错误");
        }
    }
}
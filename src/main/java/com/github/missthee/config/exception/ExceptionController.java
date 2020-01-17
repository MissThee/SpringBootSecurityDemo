package com.github.missthee.config.exception;

import com.github.missthee.config.log.builder.LogBuilder;
import com.github.missthee.tool.res.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//controller异常捕捉返回
@ApiIgnore
@RestControllerAdvice
@Order
@Slf4j
public class ExceptionController {

    //参数错误
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object httpMessageNotReadableException(HttpServletRequest request, Exception e) {
        log.debug(LogBuilder.requestLogBuilder(request, e));
        if (String.valueOf(e).contains("Required request body is missing")) {
            return Res.failure("HttpMessageNotReadableException: 请求体缺少body。" + e);
        } else {
            return Res.failure("HttpMessageNotReadableException: 无法正确读取请求中的参数。" + e);
        }

    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object missingRequestHeaderExceptionException(HttpServletRequest request, Exception e) {
        log.debug(LogBuilder.requestLogBuilder(request, e));
        String paramName = null;
        try {
            paramName = String.valueOf(e).substring(String.valueOf(e).indexOf("'") + 1, String.valueOf(e).lastIndexOf("'"));
        } catch (Exception ignored) {
        }
        return Res.failure((paramName == null ? "" : "MissingRequestHeaderException: 请求体header中缺少必须的参数【" + paramName + "】。") + e);

    }

    //运行时所有异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @Order
    public Object exceptionHandler(HttpServletRequest request, Exception e) {
        log.debug(LogBuilder.requestLogBuilder(request, e));
        e.printStackTrace();
        return Res.failure("Exception: " + e);
    }

}
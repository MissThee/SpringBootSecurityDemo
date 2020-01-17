package com.github.missthee.config.security.springsecurity.exception;

import com.github.missthee.config.log.builder.LogBuilder;
import com.github.missthee.tool.res.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

//controller异常捕捉返回
@ApiIgnore
@RestControllerAdvice
@Order(1)
@Slf4j
public class SecurityExceptionHandler {
    //访问无权限接口。返回状态403
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public Object unauthorizedException(HttpServletRequest request, AccessDeniedException e) {
        log.debug(LogBuilder.requestLogBuilder(request, null, e));
        return Res.failure(e.getClass().getSimpleName() + ":" + e.getMessage());
    }

    //需要登录。返回状态401。约定前端任何时候接收403状态码时需给用户提供登录页面
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Object unauthenticatedException(HttpServletRequest request, BadCredentialsException e) {
        log.debug(LogBuilder.requestLogBuilder(request, null, e));
        return Res.failure(e.getClass().getSimpleName() + ":" + e.getMessage());
    }
}
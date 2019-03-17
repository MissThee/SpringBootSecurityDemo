package com.server.config.log.aspect;

import com.server.config.log.builder.LogBuilder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class ControllerLogger {
    @Pointcut("execution(public * *..controller..*.*(..))")
    public void webLog() {
    }

    //返回值为方法执行的结果对象
    @Around("webLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnObj;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                stringBuilder.append(LogBuilder.requestLogAspect(request,joinPoint));
            }
        } catch (Exception e) {
            stringBuilder.append("\r\n!!!!!!!!!!!!!!!!!!!REQ-LOG-ERROR!!!!!!!!!!!!!!!!!!!");
        }
        returnObj = joinPoint.proceed();//执行方法
        try {
            if (returnObj != null) {
                stringBuilder.append(LogBuilder.responseLogAspect(returnObj));
            }
        } catch (Exception e) {
            e.printStackTrace();
            stringBuilder.append("\r\n!!!!!!!!!!!!!!!!!!!RES-LOG-ERROR!!!!!!!!!!!!!!!!!!!");
        }
        log.debug(stringBuilder.toString());
        return returnObj;
    }
}

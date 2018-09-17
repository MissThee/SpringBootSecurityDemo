package com.server.result;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
public class CustomErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }

    //自定义异常返回
    @RequestMapping(value = "/error")
    public Object error(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jO = new JSONObject();
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());

        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        if (exception != null) {
            jO.put("msg", "CustomErrorController: " + exception.getCause() );
        } else {
            jO.put("msg", "CustomErrorController: " + httpStatus.getReasonPhrase() );
        }
        ErrorLogPrinter.logOutPut(request);
        return new ResponseEntity<>(jO, httpStatus);//ResponseEntity 可动态指定返回状态码
    }
}

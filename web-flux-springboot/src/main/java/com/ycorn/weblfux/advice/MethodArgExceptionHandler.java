package com.ycorn.weblfux.advice;

import com.google.common.base.Joiner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 10:59
 * @Function:
 * @Version 1.0
 */
@RestControllerAdvice
public class MethodArgExceptionHandler {

    @ExceptionHandler({WebExchangeBindException.class})
    public ResponseEntity<String> webExchangeBindExceptionHandler(WebExchangeBindException ex) {
        Map<String, Object> errorMaps = ex.getFieldErrors().stream().collect(Collectors.toMap(f -> f.getField(), f -> f.getRejectedValue()+f.getDefaultMessage(), (v1, v2) -> v1.toString() + v2.toString()));
        String result = Joiner.on(",").withKeyValueSeparator("=").join(errorMaps);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(result);
    }

}

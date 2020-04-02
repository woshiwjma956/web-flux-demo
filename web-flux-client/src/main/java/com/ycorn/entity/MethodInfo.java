package com.ycorn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 15:45
 * @Function:
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MethodInfo {

    private String url;

    private Map<String, Object> pathParams;

    private HttpMethod httpMethod;

    private Mono<?> body;

    private Class<?> bodyType;

    private Class<?> returnType;

    private Boolean isFlux;

    private Map<String, Object> params;


}

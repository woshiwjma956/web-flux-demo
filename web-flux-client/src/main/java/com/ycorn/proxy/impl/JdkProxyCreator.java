package com.ycorn.proxy.impl;

import com.ycorn.anno.ApiService;
import com.ycorn.entity.MethodInfo;
import com.ycorn.entity.ServerInfo;
import com.ycorn.proxy.ProxyCreator;
import com.ycorn.handler.RestHandler;
import com.ycorn.handler.WebClientRestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 15:53
 * @Function:
 * @Version 1.0
 */
@Slf4j
public class JdkProxyCreator implements ProxyCreator {

    private final RestHandler restHandler = new WebClientRestHandler();

    @Override
    public Object getProxy(Class<?> clz) {

        ServerInfo serverInfo = extraServerInfo(clz);
        log.info("serverInfo :{}", serverInfo);
        restHandler.init(serverInfo);
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                MethodInfo methodInfo = extraMethodInfo(method, args);
                return restHandler.invoiceRest(methodInfo);
            }

        });
    }

    private ServerInfo extraServerInfo(Class<?> clz) {
        ApiService apiService = clz.getAnnotation(ApiService.class);
        return ServerInfo.builder().url(apiService.value()).build();
    }

    private MethodInfo extraMethodInfo(Method method, Object[] args) {
        MethodInfo methodInfo = new MethodInfo();
        extraUrlAndMethod(method, methodInfo);
        extraParams(method, args, methodInfo);
        extraReturnType(method, methodInfo);
        return methodInfo;
    }

    private void extraReturnType(Method method, MethodInfo methodInfo) {
        methodInfo.setIsFlux(method.getReturnType().isAssignableFrom(Flux.class));
        Type genericReturnType = method.getGenericReturnType();
        Class<?> returnType = this.extractElementType(genericReturnType);
        methodInfo.setReturnType(returnType);
    }

    private void extraParams(Method method, Object[] args, MethodInfo methodInfo) {
        Map<String, Object> pathParams = new LinkedHashMap<>();
        Map<String, Object> params = new LinkedHashMap<>();
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            if (args[i] != null) {
                Parameter param = parameters[i];
                PathVariable pathVariable = param.getAnnotation(PathVariable.class);
                RequestBody requestBody = param.getAnnotation(RequestBody.class);
                if (null != pathVariable) {
                    String key = pathVariable.value();
                    pathParams.put(key, args[i]);
                } else if (null != requestBody) {
                    methodInfo.setBody((Mono<?>) args[i]);
                    Type parameterizedType = param.getParameterizedType();
                    methodInfo.setBodyType(this.extractElementType(parameterizedType));
                } else {
                    params.put(param.getName(), args[i]);
                }
            }
        }
        methodInfo.setPathParams(pathParams);
        methodInfo.setParams(params);
    }

    /**
     * 得到泛型的实际类型
     *
     * @param genericReturnType
     * @return
     */
    private Class<?> extractElementType(Type genericReturnType) {
        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }

    private void extraUrlAndMethod(Method method, MethodInfo methodInfo) {
        Arrays.stream(method.getAnnotations()).forEach(anno -> {
            if (anno instanceof GetMapping) {
                GetMapping getMapping = (GetMapping) anno;
                methodInfo.setHttpMethod(HttpMethod.GET);
                methodInfo.setUrl(getMapping.value()[0]);
            }
            if (anno instanceof PostMapping) {
                PostMapping postMapping = (PostMapping) anno;
                methodInfo.setHttpMethod(HttpMethod.POST);
                methodInfo.setUrl(postMapping.value()[0]);
            }
            if (anno instanceof DeleteMapping) {
                DeleteMapping deleteMapping = (DeleteMapping) anno;
                methodInfo.setHttpMethod(HttpMethod.DELETE);
                methodInfo.setUrl(deleteMapping.value()[0]);
            }
            if (anno instanceof PutMapping) {
                PutMapping putMapping = (PutMapping) anno;
                methodInfo.setHttpMethod(HttpMethod.PUT);
                methodInfo.setUrl(putMapping.value()[0]);
            }
        });
    }
}

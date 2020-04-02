package com.ycorn.handler;

import com.google.common.base.Joiner;
import com.ycorn.entity.MethodInfo;
import com.ycorn.entity.ServerInfo;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 16:01
 * @Function:
 * @Version 1.0
 */
public class WebClientRestHandler implements RestHandler {

    private WebClient webClient;

    @Override
    public void init(ServerInfo serverInfo) {
        webClient = WebClient.create(serverInfo.getUrl());
    }

    @Override
    public Object invoiceRest(MethodInfo methodInfo) {
        WebClient.RequestBodyUriSpec requestBodyUriSpec = webClient.method(methodInfo.getHttpMethod());
        if (methodInfo.getBody() != null) {
            requestBodyUriSpec.body(methodInfo.getBody(), methodInfo.getBodyType());
        }
        if (methodInfo.getPathParams().size() > 0) {
//            requestBodyUriSpec.uri(methodInfo.getUrl(), methodInfo.getParams());
//            for (String key : methodInfo.getPathParams().keySet()) {
//                String url = methodInfo.getUrl();
//                methodInfo.setUrl(url.replaceAll("\\{" + key + "}", methodInfo.getPathParams().get(key).toString()));
//            }\
            requestBodyUriSpec.uri(methodInfo.getUrl(), methodInfo.getPathParams());
        } else {
            if (methodInfo.getParams().size() > 0) {
                String params = Joiner.on("&").withKeyValueSeparator("=").join(methodInfo.getParams());
                requestBodyUriSpec.uri(methodInfo.getUrl() + "?" + params);
            } else {
                requestBodyUriSpec.uri(methodInfo.getUrl());
            }
        }


        WebClient.ResponseSpec retrieve = requestBodyUriSpec.accept(MediaType.APPLICATION_JSON).retrieve();

        return methodInfo.getIsFlux() ? retrieve.bodyToFlux(methodInfo.getReturnType()) : retrieve.bodyToMono(methodInfo.getReturnType());
    }


    public static void main(String[] args) {
        String url = "/user/id/{id}/{age}";
        System.out.println(url.replaceAll("\\{" + "id" + "}", "123"));
    }

}

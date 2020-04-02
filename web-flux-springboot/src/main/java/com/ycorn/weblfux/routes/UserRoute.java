package com.ycorn.weblfux.routes;

import com.ycorn.weblfux.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @Author: wujianmin
 * @Date: 2020/4/1 14:57
 * @Function:
 * @Version 1.0
 */
@Configuration
public class UserRoute {

    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
        return nest(RequestPredicates.path("/user"),
                route(GET("/{id}"), userHandler::findById)
                        .andRoute(GET("/"), userHandler::findAll)
                        .andRoute(POST("/").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::create)
//                        .andRoute(POST("/valid").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::createValid)
                        .andRoute(PUT("/{id}"), userHandler::update)
                        .andRoute(DELETE("/{id}"), userHandler::delete));
    }
}

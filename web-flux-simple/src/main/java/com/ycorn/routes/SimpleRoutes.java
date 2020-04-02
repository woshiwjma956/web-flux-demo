package com.ycorn.routes;

import com.ycorn.handler.SimpleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @Author: wujianmin
 * @Date: 2020/4/1 16:01
 * @Function:
 * @Version 1.0
 */
@Configuration
public class SimpleRoutes {

    @Bean
    public RouterFunction<ServerResponse> simpleRouter(SimpleHandler simpleHandler) {
        return nest(path("/simple"),
                route(
                        GET("/test1"), simpleHandler::simple1)
                        .and(route(GET("/test2"), simpleHandler::simple2)
                        )
        );
    }
}

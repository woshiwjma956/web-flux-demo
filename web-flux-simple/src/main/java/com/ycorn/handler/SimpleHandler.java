package com.ycorn.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @Author: wujianmin
 * @Date: 2020/4/1 15:46
 * @Function:
 * @Version 1.0
 */
@Component
public class SimpleHandler {

    public Mono<ServerResponse> simple1(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("hahaha"), String.class);
    }

    public Mono<ServerResponse> simple2(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(Flux.interval(Duration.ofSeconds(1)).map(time->{
            System.out.println(Thread.currentThread().getName());
            return ThreadLocalRandom.current().nextInt();
        }), Integer.class);
    }


}

package com.ycorn.handler;

import com.ycorn.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @Author: wujianmin
 * @Date: 2020/4/1 16:38
 * @Function:
 * @Version 1.0
 */
@Component
@RequestMapping("/user")
@AllArgsConstructor
@RestController
public class UserHandler {

    //    @GetMapping(value = "/findAll", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().body(Mono.just(User.testUsers()), User.class);
    }

    //    @GetMapping(value = "/findOne", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ServerResponse> findOne(ServerRequest request) {
        String id = request.queryParam("id").get();
        User user = User.testUsers().stream().filter(u -> u.getId().equals(id)).findFirst().get();
        return ServerResponse.ok().body(Mono.just(user), User.class);
    }

    @GetMapping(value = "/findOneByOrigal", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<User> findOneByOrigal(String id) {
        return Mono.just(User.testUsers().stream().filter(u -> u.getId().equals(id)).findFirst().get());
    }

    @GetMapping(value = "/findAllByOrigal", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<List<User>> findAllByOrigal(String id) throws InterruptedException {
        return Mono.just(User.testUsers());
    }
}

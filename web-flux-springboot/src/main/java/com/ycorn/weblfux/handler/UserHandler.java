package com.ycorn.weblfux.handler;

import com.ycorn.weblfux.entity.User;
import com.ycorn.weblfux.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * @Author: wujianmin
 * @Date: 2020/4/1 11:21
 * @Function:
 * @Version 1.0
 */
@AllArgsConstructor
@Component
@RestController
public class UserHandler {

    private final UserRepository userRepository;

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(userRepository.findById(serverRequest.pathVariable("id")), User.class);
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(userRepository.findAll(), User.class);
    }

    public Mono<ServerResponse> create(@Valid ServerRequest serverRequest) {
        return serverRequest.bodyToMono(User.class).flatMap(user ->
                ServerResponse.ok().contentType(APPLICATION_JSON).body(userRepository.save(user), User.class)
        );
    }

    @PostMapping(value = "/user/valid", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> createValid(@Validated @RequestBody Mono<User> userMono) {
        return userMono.flatMap(userRepository::save);
    }

    public Mono<ServerResponse> update(@Validated ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("id");
        return userRepository.findById(userId).flatMap(user ->
                ServerResponse.ok().body(serverRequest.bodyToMono(User.class).flatMap(newUser -> {
                    user.setName(newUser.getName());
                    user.setAge(newUser.getAge());
                    return userRepository.save(user);
                }), User.class)
        ).switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("id");
        return userRepository.findById(userId).flatMap(user -> ServerResponse.ok().contentType(APPLICATION_JSON).body(userRepository.delete(user), User.class)).switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

}

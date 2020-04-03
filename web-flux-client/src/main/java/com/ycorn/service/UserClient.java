package com.ycorn.service;

import com.ycorn.anno.ApiService;
import com.ycorn.entity.User;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 15:47
 * @Function:
 * @Version 1.0
 */
@ApiService("http://localhost:8080/mvc/user/")
public interface UserClient {
    @GetMapping("/{id}")
    Mono<User> findById(@PathVariable("id") String id);

    @GetMapping("/id")
    Mono<User> findByIdParams(String id);

    @GetMapping("/")
    Flux<User> findAll();

    @PostMapping(value = "/valid", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<User> createValid(@Validated @RequestBody Mono<User> userMono);

    @DeleteMapping("/{id}")
    Mono<Void> delete(@PathVariable("id") String id);

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<User> update(@RequestBody @Validated Mono<User> userMono);
}

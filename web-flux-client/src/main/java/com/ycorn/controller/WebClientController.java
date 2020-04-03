package com.ycorn.controller;

import com.ycorn.entity.User;
import com.ycorn.service.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 16:52
 * @Function:
 * @Version 1.0
 */
@RestController
@RequestMapping("/webClient")
public class WebClientController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/user/{id}")
    public Mono<User> findById(@PathVariable("id") String id) {
        return userClient.findById(id);
    }

    @GetMapping("/user/id")
    public Mono<User> findByIdParams(String id) {
        return userClient.findByIdParams(id);
    }

    @GetMapping("/user")
    public Flux<User> findAll() {
        return userClient.findAll();
    }

    @PostMapping(value = "/valid", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<User> createValid(@Validated @RequestBody Mono<User> userMono) {
        return userClient.createValid(userMono);
    }

    @DeleteMapping("/{id}")
    Mono<Void> delete(@PathVariable("id") String id) {
        return userClient.delete(id);
    }

    @PutMapping("/")
    Mono<User> update(@RequestBody @Validated Mono<User> userMono) {
        return userClient.update(userMono);
    }

}

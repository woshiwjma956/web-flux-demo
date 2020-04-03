package com.ycorn.weblfux.handler;

import com.ycorn.weblfux.entity.User;
import com.ycorn.weblfux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 17:24
 * @Function:
 * @Version 1.0
 */
@RestController
@RequestMapping("/mvc/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public Mono<User> findById(@PathVariable("id") String id) {
        return userRepository.findById(id);
    }

    @GetMapping("/id")
    public Mono<User> findByIdParams(@RequestParam("id") String id) {
        return userRepository.findById(id);
    }

    @GetMapping("/")
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @PostMapping(value = "/valid", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> createValid(@Validated @RequestBody Mono<User> userMono) {
        return userMono.flatMap(userRepository::save);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return userRepository.deleteById(id);
    }

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> update(@Validated @RequestBody Mono<User> userMono) {
        return userMono.flatMap(u -> {
            System.out.println(u);
            if (userRepository.findById(u.getId()) != null) {
                return userRepository.save(u);
            } else {
                return Mono.empty();
            }
        });
    }
}

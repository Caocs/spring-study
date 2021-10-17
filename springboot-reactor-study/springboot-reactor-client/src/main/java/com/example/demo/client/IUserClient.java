package com.example.demo.client;

import com.example.demo.framework.ApiServer;
import com.example.demo.data.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ApiServer("http://localhost:8080/user")
public interface IUserClient {

    @GetMapping("/all")
    Flux<User> getAllUser();

    @GetMapping("/get/{id}")
    public Mono<User> findById(@PathVariable("id") String id);

    @PostMapping("/create")
    public Mono<User> createUser(@RequestBody Mono<User> user);

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteUser(@PathVariable("id") String id);

    @PutMapping("/update/{id}")
    public Mono<User> updateUser(@PathVariable("id") String id, @RequestBody User user);


}

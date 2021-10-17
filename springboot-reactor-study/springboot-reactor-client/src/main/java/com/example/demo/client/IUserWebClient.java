package com.example.demo.client;

import com.example.demo.aspect.WebClientApi;
import com.example.demo.data.User;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserWebClient {

    @WebClientApi(baseUrl = "http://localhost:8080", restUrl = "/user/all", httpMethod = HttpMethod.GET)
    Flux<User> getAllUser();

    @WebClientApi(baseUrl = "http://localhost:8080", restUrl = "/user/get/{id}", httpMethod = HttpMethod.GET)
    public Mono<User> findById(@PathVariable("id") String id);

    @WebClientApi(baseUrl = "http://localhost:8080", restUrl = "/user//create", httpMethod = HttpMethod.POST)
    public Mono<User> createUser(@RequestBody Mono<User> user);
}

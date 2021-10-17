package com.example.demo.controller;

import com.example.demo.data.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author caocs
 * @date 2021/10/1
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 官方推荐使用构造函数进行依赖注入
     * 降低耦合。
     */
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 以数组形式一次性返回数据
     */
    @GetMapping("/all")
    public Flux<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * 以SSE形式多次返回数据
     */
    @GetMapping(value = "/all/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getAllUserStream() {
        return userRepository.findAll();
    }

    @PostMapping("/create")
    public Mono<User> createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * 如果要操作数据并返回一个Mono，这个时候使用flatMap。
     * 如果不操作数据只是转换数据，使用map。
     */
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(
            @PathVariable("id") String id) {
        return userRepository.findById(id)
                .flatMap((user) -> {
                    return userRepository.delete(user)
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)));
                })
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    /**
     * 修改数据
     * 存在的时候返回200及修改后的数据，
     * 不存在的时候返回404。
     *
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<User>> updateUser(
            @PathVariable("id") String id,
            @RequestBody User user) {
        return userRepository.findById(id)
                .flatMap((u) -> {
                    u.setName(user.getName());
                    u.setAge(user.getAge());
                    return userRepository.save(u);
                })
                .map((u) -> {
                    return new ResponseEntity<User>(u, HttpStatus.OK);
                })
                .defaultIfEmpty(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<User>> findById(@PathVariable("id") String id) {
        return userRepository.findById(id)
                .map((u) -> {
                    return new ResponseEntity<User>(u, HttpStatus.OK);
                })
                .defaultIfEmpty(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/age/{start}/{end}")
    public Flux<User> findByAge(
            @PathVariable("start") int start,
            @PathVariable("end") int end) {
        return userRepository.findByAgeBetween(start, end);
    }

    @GetMapping(value = "/age/stream/{start}/{end}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findByAgeStream(
            @PathVariable("start") int start,
            @PathVariable("end") int end) {
        return userRepository.findByAgeBetween(start, end);
    }

    @GetMapping("/old")
    public Flux<User> findOldUser() {
        return userRepository.oldUser();
    }

}

package com.example.demo.controller;

import com.example.demo.client.IUserWebClient;
import com.example.demo.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author caocs
 * @date 2021/10/1
 */
@RestController
@RequestMapping("/test/webclient")
public class TestWebClientController {

    @Autowired
    IUserWebClient userWebClient;

    @GetMapping("all")
    public void test() {
        Flux<User> users = userWebClient.getAllUser();
        users.subscribe(System.out::println);

        String id = "6156829f1ed9f567866a25f1";
        userWebClient.findById(id)
                .subscribe((user) -> {
                    System.out.println(user);
                }, e -> {
                    System.out.println("找不到啊" + e.getMessage());
                });
//        userClient.deleteUser(id)
//                .subscribe();

        User user = new User();
        user.setName("webclient-2");
        user.setAge(20);
        userWebClient.createUser(Mono.just(user))
                .subscribe((u) -> {
                    System.out.println(u);
                });
    }

}

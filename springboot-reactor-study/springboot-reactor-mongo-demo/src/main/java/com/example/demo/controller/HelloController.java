package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author caocs
 * @date 2021/9/30
 */
@RestController
@RequestMapping(value = "/demo", method = RequestMethod.GET)
public class HelloController {

    @GetMapping("/hello")
    public String getHello() {
        System.out.println("hello start " + System.currentTimeMillis());
        String result = getStr();
        System.out.println("hello end   " + System.currentTimeMillis());
        return result;
    }

    @GetMapping("/mono")
    public Mono<String> getMono() {
        System.out.println("mono start " + System.currentTimeMillis());
        Mono<String> result = Mono.fromSupplier(() -> getStr());
        System.out.println("mono end   " + System.currentTimeMillis());
        return result;
    }

    /**
     * MediaType.TEXT_EVENT_STREAM_VALUE：指定数据像流一样输出
     * @return
     */
    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getFlux() {
        System.out.println("flux start " + System.currentTimeMillis());
        Flux<String> result = Flux.fromStream(IntStream.range(1, 5).mapToObj(i -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "我终于执行到了-" + i + "\n";
        }));
        System.out.println("flux end  " + System.currentTimeMillis());
        return result;
    }

    private String getStr() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "我终于执行到了";
    }

}

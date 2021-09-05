package com.dingdongdeng.springboot.controller;

import com.dingdongdeng.springboot.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final WebClient webClient;

    /**
     * 1. 왜 case1이 case2보다 오래걸리는거지?? 2. block() 메소드를 어떻게해야 합리적으로 사용하는걸까??? << 내가 바라는 형태의 동작은 어떻게....?
     */

    @GetMapping("/case/1")
    public String case1() {
        long current = System.currentTimeMillis();
        Mono<String> res = webClient.get()
            .uri("/test/target/1")
            .retrieve()
            .bodyToMono(String.class);
        log.info("flag get Mono : {}", System.currentTimeMillis() - current + "ms");
        heavyProcess();
        log.info("flag heavyProcess() : {}", System.currentTimeMillis() - current + "ms");
        log.info("flag start res.block()");
        String resValue = res.block();
        log.info("flag res.block() : {}, result = {}", System.currentTimeMillis() - current + "ms", resValue);
        return resValue;
    }

    @GetMapping("/case/2")
    public String case2() {
        long current = System.currentTimeMillis();
        Mono<String> res = webClient.get()
            .uri("/test/target/1")
            .retrieve()
            .bodyToMono(String.class);
        log.info("flag get Mono : {}", System.currentTimeMillis() - current + "ms");
        log.info("flag start res.block()");
        String resValue = res.block();
        log.info("flag res.block() : {}, result = {}", System.currentTimeMillis() - current + "ms", resValue);
        heavyProcess();
        log.info("flag heavyProcess() : {}", System.currentTimeMillis() - current + "ms");
        return resValue;
    }

    @GetMapping("/case/3")
    public String case3() {
        long current = System.currentTimeMillis();
        Mono<String> res1 = webClient.get()
            .uri("/test/target/1")
            .retrieve()
            .bodyToMono(String.class);
        Mono<String> res2 = webClient.get()
            .uri("/test/target/1")
            .retrieve()
            .bodyToMono(String.class);
        Mono<String> res3 = webClient.get()
            .uri("/test/target/1")
            .retrieve()
            .bodyToMono(String.class);

        log.info("flag get Mono 1,2,3 : {}", System.currentTimeMillis() - current + "ms");
        String resValue1 = res1.block();
        log.info("flag res1.block() : {}", System.currentTimeMillis() - current + "ms");
        String resValue2 = res2.block();
        log.info("flag res2.block() : {}", System.currentTimeMillis() - current + "ms");
        String resValue3 = res3.block();
        log.info("flag res3.block() : {}", System.currentTimeMillis() - current + "ms");
        return "something....";
    }

    @GetMapping("/case/4")
    public String case4() {
        long current = System.currentTimeMillis();
        webClient.get()
            .uri("/test/target/1")
            .retrieve()
            .bodyToMono(String.class)
            .subscribe(res -> log.info("subscribe result : {}", res));
        log.info("flag subscribe : {}", System.currentTimeMillis() - current + "ms");
        return "something....";
    }

    @GetMapping("/case/5")
    public String case5(String httpStatus) {
        String response = "";
        try {
            response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/test/target/2")
                    .queryParam("httpStatus", httpStatus)
                    .build()
                )
                .retrieve()
                .bodyToMono(String.class)
//                .exchangeToMono(res -> res.toEntity(String.class))
//                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(() -> new CustomException("400 에러")))
//                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(() -> new CustomException("500 에러")))
//                .doOnSuccess(res -> {
//                    log.info("res : {}", res);
//                })
//                .doOnError()
                .block();

        } catch (WebClientResponseException e) {
            log.error("web client response error ", e);
        } catch (WebClientRequestException e) {
            log.error("web client request error", e);
        } catch (WebClientException e) {
            log.error("web client error", e);
        } catch (CustomException e) {
            log.error("custom error ", e);
        } catch (Exception e) {
            log.error("error", e);
        }
        return response;
    }

    @GetMapping("/target/1")
    public String target1() {
        long current = System.currentTimeMillis();
        log.info("target1 start : {}", System.currentTimeMillis() - current + "ms");
        heavyProcess();
        log.info("target1 end : {}", System.currentTimeMillis() - current + "ms");
        return "This is heavy process result." + System.currentTimeMillis();
    }

    @GetMapping("/target/2")
    public ResponseEntity target2(int httpStatus) {
        return ResponseEntity.status(httpStatus).body("response status is " + httpStatus);
    }

    private void heavyProcess() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

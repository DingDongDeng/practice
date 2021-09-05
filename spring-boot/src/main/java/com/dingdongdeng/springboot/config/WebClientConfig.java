package com.dingdongdeng.springboot.config;

import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl("http://localhost:8080")
            .filters(exchangeFilterFunctions -> {
                exchangeFilterFunctions.add(logResFilter());
                exchangeFilterFunctions.add(logReqFilter());
                exchangeFilterFunctions.add(mdcFilter());
            })
            .build();
    }

    private ExchangeFilterFunction logReqFilter() {
        return ExchangeFilterFunction.ofRequestProcessor((clientRequest) -> {
            StringBuilder logReqBuilder = new StringBuilder();
            logReqBuilder.append("url : ").append(clientRequest.url()).append("\n");
            logReqBuilder.append("method : ").append(clientRequest.method()).append("\n");
            logReqBuilder.append("headers : ").append(clientRequest.headers()).append("\n");
//            logReqBuilder.append(", body : ").append(clientRequest.body().toString()); //fixme body도 로깅하고 싶
            log.info("webclient request: {}", logReqBuilder.toString());

            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> clientResponse
            .bodyToMono(String.class) //fixme 이미 응답이 컨슈밍돼서 block했을때 비어있음
            .flatMap(body -> {
                StringBuilder logResBuilder = new StringBuilder();
                logResBuilder.append("status : ").append(clientResponse.statusCode()).append("\n");
                logResBuilder.append(", headers : ").append(clientResponse.headers().asHttpHeaders().toString()).append("\n");
                logResBuilder.append(", body : ").append(body).append("\n");

                log.info("webclient response : {}", logResBuilder.toString());
                return Mono.just(clientResponse);
            })
        );
    }

    private ExchangeFilterFunction mdcFilter() {
        return (clientRequest, next) -> {
            Map<String, String> context = MDC.getCopyOfContextMap();
            return next.exchange(clientRequest).doOnNext(value -> {
                if (Objects.nonNull(context)) {
                    MDC.setContextMap(context);
                }
            });
        };
    }
}

package com.dingdongdeng.springboot.controller;

import com.dingdongdeng.springboot.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/test")
@RestController
public class ExceptionTestController {

    private final TestService testService;

    @GetMapping("/exception")
    public String exceptionTest(@RequestParam Integer type) {
        testService.exceptionTest(type);
        return "success";
    }
}

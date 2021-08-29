package com.dingdongdeng.springboot.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void case1() throws Exception {
        long current = System.currentTimeMillis();
        mockMvc.perform(get("/test/case/1")).andExpect(status().isOk());
        log.info("case 1 : {}", System.currentTimeMillis() - current + "ms");
    }

    @Test
    void case2() throws Exception {
        long current = System.currentTimeMillis();
        mockMvc.perform(get("/test/case/2")).andExpect(status().isOk());
        log.info("case 2 : {}", System.currentTimeMillis() - current + "ms");
    }

    @Test
    void case3() throws Exception {
        long current = System.currentTimeMillis();
        mockMvc.perform(get("/test/case/3")).andExpect(status().isOk());
        log.info("case 3 : {}", System.currentTimeMillis() - current + "ms");
    }


    @Test
    void target1() throws Exception {
         ResultActions result = mockMvc.perform(get("/test/target/1"));
         log.info("result : {}", result.andReturn().getResponse().getContentAsString());
    }

}
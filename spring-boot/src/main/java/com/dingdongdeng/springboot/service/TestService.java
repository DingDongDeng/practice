package com.dingdongdeng.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    public void exceptionTest(int type) {
        ExceptionHandler exceptionHandler = new ExceptionHandler();

        try {
            if (type == 1) {
                throw new RuntimeException("에러 발생");
            }
            if (type == 2) {
                throw new RootException("에러 발생");
            }
            if (type == 3) {
                throw new ChildException("에러 발생");
            }
            if (type == 4) {
                throw new Child2Exception("에러 발생");
            }
            if (type == 5) {
                throw new AnotherException("에러 발생");
            }
            if (type == 6) {
                throw new Exception("에러 발생");
            }
        } catch (Exception e) {
            exceptionHandler.handler(e);
        }
    }

    public class ExceptionHandler {

        public void handler(RuntimeException e) {
            log.info("RuntimeException :: {}", e.getMessage());
        }

        public void handler(RootException e) {
            log.info("RootException :: {}", e.getMessage());
        }

        public void handler(ChildException e) {
            log.info("ChildException :: {}", e.getMessage());
        }

        public void handler(Child2Exception e) {
            log.info("Child2Exception :: {}", e.getMessage());
        }

        public void handler(AnotherException e) {
            log.info("AnotherException :: {}", e.getMessage());
        }

        public void handler(Exception e) {
            if(e instanceof AnotherException){
                log.info("Hello, AnotherException");
            }
            log.info("Exception :: {}", e.getMessage());
        }
    }

    public class RootException extends RuntimeException {

        public RootException(String message) {
            super(message);
        }
    }

    public class ChildException extends RootException {

        public ChildException(String message) {
            super(message);
        }
    }

    public class Child2Exception extends RootException {

        public Child2Exception(String message) {
            super(message);
        }
    }


    public class AnotherException extends RuntimeException {

        public AnotherException(String message) {
            super(message);
        }
    }


}

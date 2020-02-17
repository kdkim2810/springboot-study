package com.std.boot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    메인 클래스
    @SpringBootApplication으로 인해 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 자동으로 함
    @SpringBootApplication이 있는 위치부터 설정을 읽어가기 때문에 항상 프로젝트의 최상단에 위치해야 함
*/
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        // 내장 was 실행
        SpringApplication.run(Application.class, args);
    }
}

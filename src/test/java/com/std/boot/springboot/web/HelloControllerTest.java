package com.std.boot.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
    테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
    여기서부터는 SpringRunner라는 스프링 실행자를 사용
    즉, 스프링 부트 테스트와 Junit사이에 연결자 역할을 함
*/
@RunWith(SpringRunner.class)
/*
    여러 스프링 테스트 어노테이션 중, Spring MVC에만 집중할 수 있는 어노테이션
    선언할 경우 @Controller이나, @ControllerAdvice등을 사용할 수 있음
    단 @Service, @Component, @Repository등은 사용할 수 없음
    여기서는 컨트롤러만 사용하기 때문에 선언
*/
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {
    // 스프링이 관리하는 빈을 주입받음
    @Autowired
    /*
        웹 API를 테스트할 때 사용
        스프링 MVC 테스트의 시작점
        이 클래스를 통해 HTTP HET, POST등에 대한 API 테스트를 할 수 있음
    */
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";
        /*
            MockMvc를 통해 /hello 주소로 HTTP GET요청을 함
            체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언할 수 있음
        */
        mvc.perform(get("/hello"))
                /*
                    mvc.perform의 결과를 검증
                    HTTP header의 Status를 검증 ex) 200, 404, 500
                */
                .andExpect(status().isOk())
                /*
                    mvc.perform의 결과를 검증
                    응답 본문의 내용을 검증
                    Controller에서 리턴하는 hello가 제대로 리턴되는지 검증
                */
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                /*
                    API 테스트할 떄 사용될 요청 파라미터를 설정
                    값은 String만 허용되기 때문에 숫자/날짜 같은 데이터를 등록할 때는 문자열로 변경해야함
                */
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                /*
                    JSON 응답값을 필드별로 검증할 수 있는 메소드
                    $를 기준으로 필드명을 명시
                */
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}

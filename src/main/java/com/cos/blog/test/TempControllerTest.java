package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

    //http://localhost:8088/blog/temp/home
    @GetMapping("/temp/home")
    public String tempHome(){
        System.out.println("tempHome()");
        // 파일리턴 기본경로 : src/main/resources/static
        // 리턴명 : /home.html
        return "/home.html";
    }
    //정적 파일은 잘됨, jsp는 안됨! => pom.xml에서 jsp 의존성을 추가해야함
    @GetMapping("/temp/img")
    public String tempImg(){
        return "/국비 QR.png";
    }

    @GetMapping("/temp/jsp")
    public String tempjsp() {
        //yml설정에 의해 prefix가 앞뒤로 붙음
        //풀네임 : /WEB-INF/views/test.jsp
        return "test";
    }

}

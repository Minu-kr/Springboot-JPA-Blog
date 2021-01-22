package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

//사용자가 요청 - > 응답(Data)
//@Controller -> HTML 응답
@RestController
public class HttpControllerTest {
    private static final String TAG = "HttpController Test :";

    //http://localhost:8088/blog/http/lombok
    @GetMapping("/http/lombok")
    public String lombokTest(){
        Member m = Member.builder().id(123).username("ssar").password("1234").email("ddd").build();
        System.out.println(TAG+"getter : "+m.getId());
        m.setId(5000);
        System.out.println(TAG+"setter : "+m.getId());
        return "lombok test 완료";
    }
    //인터넷 브라우저 요청은 get요청만 가능하다
    //http://localhost:8088/blog/http/get (select)
    @GetMapping("/http/get")
    public String getTest(Member m){
        return "get 요청 : "+ m.getId() +", "+m.getUsername()+", "+m.getEmail();
    }
    //http://localhost:8088/blog/http/post (insert)
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m){ //MessageConverter가 mapping 역할 수행
        return "post 요청 : "+ m.getId() +", "+m.getUsername()+", "+m.getEmail();
    }

    //http://localhost:8088/blog/http/put (update)
    @PutMapping("/http/put")
    public String putTest(){
        return "put 요청";
    }

    //http://localhost:8088/blog/http/delete (delete)
    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "delete 요청";
    }


}

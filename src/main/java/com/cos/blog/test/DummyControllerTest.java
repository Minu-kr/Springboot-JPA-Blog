package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.function.Supplier;

//html파일이 아니라 data를 리턴해주는 RestController
@RestController
public class DummyControllerTest {

    //메모리에 같이 적재
    @Autowired
    private UserRepository userRepository;

    //http://localhost:8088/blog/dummy/user/
    @DeleteMapping("/dummy/user/{id}")
    public String deleteUser(@PathVariable int id){
        try {
            userRepository.deleteById(id);
        }catch (IllegalArgumentException e) {
            return "삭제 실패!";
        }
        return "삭제완료 : id : " + id;
    }

    //save함수는 id를 전달하지 않으면 insert를 해주고
    //id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    //만약 데이터가 없으면 insert를 함.
    //http://localhost:8088/blog/dummy/user/
    @Transactional //메서드가 종료되면 자동으로 commit을 실시함
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){
        System.out.println("id : " + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정 실패");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        //userRepository.save(user);
        //더티 체킹

        return user;
    }

    //http://localhost:8088/blog/dummy/user/
    @GetMapping("/dummy/user")
    public List<User> list(){
        return userRepository.findAll();
    }
    
    //한페이지당 2건의 데이터를 리턴받아 보기
    @GetMapping("/dummy/user/page")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        Page<User> pagingUser = userRepository.findAll(pageable);
        List<User> users = pagingUser.getContent();
        return users;
    }

    //http://localhost:8088/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        //user/4를 실행하면 user = null 됨 null return 가능?
        //Optional로 너의 User 객체를 가져올테니 판단후 리턴할 것
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저 없어용. id :" + id);
            }
        });
        // 요청 : 웹브라우져
        //user 객체 = 자바 오브젝트
        //변환 (웹브라우저가 이해할 수 있는 데이터) -> Json
        //스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
        //만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
        //user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
        return user;
    }

    @PostMapping("/dummy/join")
    public String join(User user){
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getEmail());
        System.out.println(user.getRole());
        System.out.println(user.getCreateDate());

        //enum을 통해 데이터를 강제시킬 수 있다.
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입 완료";
    }
}

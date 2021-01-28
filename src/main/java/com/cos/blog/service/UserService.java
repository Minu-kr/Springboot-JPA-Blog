package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록, IOC(제어의역전) 해준다
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public User 회원찾기(String username){
        User user = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });
        return user;
    }


    @Transactional
    public void 회원가입(User user) {
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            user.setRole(RoleType.USER);
            user.setPassword(encPassword);
            userRepository.save(user);
    }
    @Transactional
    public void 회원수정(User user) {
        //수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, User오브젝트를 수정
        //영속화된 오브젝트를 변경하면 자동으로 DB에 update실시

        User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원찾기실패");
        });
        //Validate 체크
        if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistance.setPassword(encPassword);
            persistance.setEmail(user.getEmail());
        }
        //회원수정 함수 종료시 => 서비스 종료 => 트랜잭션 종료 => 자동 commit
    }
}
//서비스가 필요한 이유
//1. 트랜잭션 관리
//2. 서비스 의미

//Select할 때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료 (정합성유지)
//    @Transactional(readOnly = true)
//    public User 로그인(User user) {
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
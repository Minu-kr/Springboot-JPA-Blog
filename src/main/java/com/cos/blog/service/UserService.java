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
    private BCryptPasswordEncoder encode;

    @Transactional
    public void 회원가입(User user) {
            String rawPassword = user.getPassword();
            String encPassword = encode.encode(rawPassword);
            user.setRole(RoleType.USER);
            user.setPassword(encPassword);
            userRepository.save(user);
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
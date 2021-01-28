package com.cos.blog.repository;

import com.cos.blog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

//해당 레파지토리는 유저 테이블을 관리함, 기본키는 숫자임
//DAO
//자동으로 bean등록이 된다.
//@Repository 생략 가능
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
//JPA Naming 쿼리
// SELECT * FROM user WHERE username = ?1 AND password = ?2;
//User findByUsernameAndPassword(String username, String password);
//같은 의미
//@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//User login(String username, String password);
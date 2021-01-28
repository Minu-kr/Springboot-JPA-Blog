package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
//User 클래스가  자동으로 MySQL에 테이블이 생성 된다.

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@DynamicInsert insert시에 null인 필드를 제외시켜준다
public class User {
    @Id //primary key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링전략을 따라간다.
    private int id; //시퀀스, auto_increment

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 100) //나중에 hash로 암호화하려면 낭낭하게 줘야함
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    //@ColumnDefault("user")
    //DB는 RoleType이 없음
    @Enumerated(EnumType.STRING)
    private RoleType role; //Enum을 쓰는게 좋다. // admin, user, manager 등등 권한 나누기

    private String oauth; // kakao, google

    @CreationTimestamp //시간이 자동으로 입력됨
    private Timestamp createDate;
}

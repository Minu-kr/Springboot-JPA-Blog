package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량 데이터 사용시
    private String content; //섬머노트 라이브러리 사용 <html> 태그 포함 =>  용량 큼

    private int count; //조회수

    //기본전략 : EAGER -> 무조건 가져오기
    @ManyToOne(fetch = FetchType.EAGER) //Many=Board. User=One => 한명의 user는 여러 게시글을 쓸 수 있다.
    @JoinColumn(name="userId")
    private User user; //DB는 오브젝트를 저장할 수 없다. 자바는 가능

    //기본전략 : LAZY -> 필요할때만 가져오기
    //board조회시 join을 통해 reply의 board를 가져와야함
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //mappedBy 연관관계의 주인이아니다(FK아님) DB에 컬럼생성X
    @JsonIgnoreProperties({"board"}) //무한참조 방지
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp
    private Timestamp createDate;


}

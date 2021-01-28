package com.cos.blog.service;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyReposiotry;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록, IOC(제어의역전) 해준다
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReplyReposiotry replyReposiotry;

    @Transactional
    public void 글쓰기(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(int id){
        return boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 상세보기 실패:");
        });
    }

    @Transactional
    public void 글삭제하기(int id){
            boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(int id, Board requestBoard){
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패:");
        }); //영속화 완료
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        //해당 함수로 종료시에 트랜잭션이 Service가 종료될 때 트랜잭션이 종료된다.
        //더티체킹 발생 -> 자동 업데이트가 됨. DB flush
    }

    @Transactional
    public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto){
        User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
            return new IllegalArgumentException("댓글 쓰기 실패: 유저 id찾을수 없음.");
        }); //영속화 완료


        Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
            return new IllegalArgumentException("댓글 쓰기 실패: 게시글 id찾을수 없음.");
        }); //영속화 완료

        Reply reply = Reply.builder()
                .user(user)
                .board(board)
                .content(replySaveRequestDto.getContent())
                .build();

        replyReposiotry.save(reply);
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
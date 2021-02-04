package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

//데이터만 리턴해주는 restcontroller 사용
@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;


    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.글쓰기(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        //자바오브젝트를 JSON으로 변환해서 리턴 => jackson 라이브러리
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id, @AuthenticationPrincipal PrincipalDetail principal){
        Board board = boardRepository.findById(id).get();
        if(board.getUser().getId() == principal.getUser().getId()) {
            boardService.글삭제하기(id);
            return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        }
        else{
            return new ResponseDto<Integer>(HttpStatus.OK.value(), -1);
        }
    }

    @PutMapping("/api/board/{id}")
    public  ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
         boardService.글수정하기(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //데이터 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
    //dto 사용하지 않은 이유는
    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.댓글쓰기(replySaveRequestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        //자바오브젝트를 JSON으로 변환해서 리턴 => jackson 라이브러리
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
        boardService.댓글삭제(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        //자바오브젝트를 JSON으로 변환해서 리턴 => jackson 라이브러리
    }
    /*//전통적 방식의 로그인 방법법
   @PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
        System.out.println("UserApiController : login 호출됨");
        User principal = userService.로그인(user);

        if(principal != null){
            session.setAttribute("principal", principal);
        }
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }*/


}

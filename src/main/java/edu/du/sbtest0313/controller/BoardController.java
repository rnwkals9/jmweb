package edu.du.sbtest0313.controller;

import edu.du.sbtest0313.entity.Board;
import edu.du.sbtest0313.entity.User;
import edu.du.sbtest0313.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시글 목록
    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boardList = boardService.getAllBoards();
        model.addAttribute("boardList", boardList);
        return "board/list";
    }

    // 게시글 작성 폼
    @GetMapping("/write")
    public String writeForm(HttpSession session, Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/user/login";
        }

        model.addAttribute("board", new Board());
        return "board/write";
    }

    // 게시글 저장
    @PostMapping("/save")
    public String save(@ModelAttribute Board board, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        boardService.createBoard(board, loginUser.getId());
        return "redirect:/board/list";
    }

    // 게시글 상세 보기
    @GetMapping("/view")
    public String view(@RequestParam("id") Long id, Model model) {
        Board board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        return "board/view";
    }

    // 게시글 수정 폼
    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model, HttpSession session) {
        Board board = boardService.getBoardById(id);
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null || !board.getUser().getId().equals(loginUser.getId())) {
            return "redirect:/board/list";
        }

        model.addAttribute("board", board);
        return "board/edit";
    }

    // 게시글 수정 처리
//    @PostMapping("/update")
//    public String update(@ModelAttribute Board board, HttpSession session) {
//        User loginUser = (User) session.getAttribute("loginUser");
//        if (loginUser == null || !board.getUser().getId().equals(loginUser.getId())) {
//            return "redirect:/board/list";
//        }
//
//        board.setUser(loginUser); // 작성자 정보 유지
//        boardService.updateBoard(board);
//        return "redirect:/board/list";
//    }

    @PostMapping("/update")
    public String update(@ModelAttribute Board board, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        // DB에서 원래 board를 다시 조회해서 작성자 확인
        Board originalBoard = boardService.getBoardById(board.getId());
        if (!originalBoard.getUser().getId().equals(loginUser.getId())) {
            return "redirect:/board/list";
        }

        board.setUser(loginUser); // 작성자 정보 다시 세팅
        boardService.updateBoard(board);
        return "redirect:/board/list";
    }


    // 게시글 삭제
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id, HttpSession session) {
        Board board = boardService.getBoardById(id);
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser != null && board.getUser().getId().equals(loginUser.getId())) {
            boardService.deleteBoard(id);
        }

        return "redirect:/board/list";
    }
}

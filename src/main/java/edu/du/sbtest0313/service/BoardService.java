package edu.du.sbtest0313.service;

import edu.du.sbtest0313.entity.Board;
import edu.du.sbtest0313.entity.User;
import edu.du.sbtest0313.repository.BoardRepository;
import edu.du.sbtest0313.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    // 게시글 작성
    public void createBoard(Board board, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        board.setUser(user);
        boardRepository.save(board);
    }

    // 게시글 목록
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    // 게시글 상세
    public Board getBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    // 게시글 수정
    public void updateBoard(Board updatedBoard) {
        boardRepository.save(updatedBoard);
    }

    // 게시글 삭제
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    // 내가 쓴 글 목록
    public List<Board> getBoardsByUser(Long userId) {
        return boardRepository.findByUserId(userId);
    }
}

package edu.du.sbtest0313.repository;

import edu.du.sbtest0313.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByUserId(Long userId);
}

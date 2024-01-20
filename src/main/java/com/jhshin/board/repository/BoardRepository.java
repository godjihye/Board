package com.jhshin.board.repository;

import com.jhshin.board.model.BoardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository {
    Optional<BoardDTO> findById(Long id);
}

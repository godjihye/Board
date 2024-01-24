package com.jhshin.board.repository;

import com.jhshin.board.entity.CommentEntity;
import com.jhshin.board.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByPostId(Long postId);
}

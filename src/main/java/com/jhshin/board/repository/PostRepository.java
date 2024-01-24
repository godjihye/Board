package com.jhshin.board.repository;

import com.jhshin.board.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {


}

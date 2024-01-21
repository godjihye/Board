package com.jhshin.board.repository;

import com.jhshin.board.entity.PostEntity;
import com.jhshin.board.model.PostDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface PostRepository extends JpaRepository<PostEntity, Long> {


}

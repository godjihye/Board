package com.jhshin.board.service;

import com.jhshin.board.entity.PostEntity;
import com.jhshin.board.entity.UserEntity;
import com.jhshin.board.model.PostDTO;
import com.jhshin.board.model.UserDTO;
import com.jhshin.board.repository.PostRepository;
import com.jhshin.board.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Transactional
    public void add(PostDTO postDTO) {
        UserEntity userEntity = userRepository.findByLoginId(postDTO.getWriter().getLoginId()).orElseThrow(
                () -> new IllegalStateException("사용자가 존재하지 않습니다.")
        );

        PostEntity postEntity = new PostEntity();
        // PostDTO의 데이터를 PostEntity로 복사
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setContent(postDTO.getContent());
        postEntity.setCreatedAt(postDTO.getCreatedAt());
        postEntity.setWriter(userEntity);

        System.out.println("postDTO.content = " + postDTO.getContent());
        postRepository.save(postEntity);
    }



    public List<PostDTO> findAll() {
        List<PostEntity> postEntityList = postRepository.findAll();
        List<PostDTO> allPosts = new ArrayList<>();
        for (PostEntity post : postEntityList) {
            allPosts.add(PostDTO.toPostDTO(post));
        }
        return allPosts;
    }

    public PostDTO find(Long id) {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        return postEntity.map(PostDTO::toPostDTO).orElse(null);
    }
}

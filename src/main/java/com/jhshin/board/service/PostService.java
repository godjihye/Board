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
import org.springframework.data.domain.Sort;
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
    public void addPost(PostDTO postDTO) {
        UserEntity userEntity = userRepository.findByLoginId(postDTO.getWriter().getLoginId()).orElseThrow(
                () -> new IllegalStateException("사용자가 존재하지 않습니다.")
        );
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setContent(postDTO.getContent());
        postEntity.setCreatedAt(postDTO.getCreatedAt());
        postEntity.setWriter(userEntity);
        postRepository.save(postEntity);
    }
    public List<PostDTO> findAllPosts() {
        List<PostEntity> postEntityList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<PostDTO> allPosts = new ArrayList<>();
        for (PostEntity post : postEntityList) {
            allPosts.add(PostDTO.toPostDTO(post));
        }
        return allPosts;
    }
    public PostDTO findPost(Long id) {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        return postEntity.map(PostDTO::toPostDTO).orElse(null);
    }

    public void removePost(Long postId) {
        Optional<PostEntity> postEntity = postRepository.findById(postId);
        if(postEntity.isPresent()){
            postRepository.delete(postEntity.get());
        }
    }

    public void editPost(PostDTO postDTO) {
        Optional<PostEntity> postEntity = postRepository.findById(postDTO.getId());
        if(postEntity.isPresent()){
            PostEntity editPost = postEntity.get();
            editPost.setTitle(postDTO.getTitle());
            editPost.setContent(postDTO.getContent());
            editPost.setUpdatedAt(postDTO.getUpdatedAt());
            postRepository.save(editPost);
        }
        System.out.println("수정할 post를 찾지 못했습니다.");
    }
}

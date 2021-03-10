package com.rangjin.springbootblog.service;

import com.rangjin.springbootblog.domain.PageRequest;
import com.rangjin.springbootblog.domain.post.Post;
import com.rangjin.springbootblog.domain.post.PostRepository;
import com.rangjin.springbootblog.domain.post.PostStatus;
import com.rangjin.springbootblog.web.dto.PostRequestDto;
import com.rangjin.springbootblog.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Long create(PostRequestDto dto) {
        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .status(dto.getStatus())
                .build();

        return postRepository.save(post).getId();
    }

    public PostResponseDto findById(Long id) {
        return new PostResponseDto(postRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    public Page<PostResponseDto> findByStatus(PageRequest pageRequest) {
        return postRepository.findByStatus(PostStatus.Public, pageRequest.of()).map(PostResponseDto::new);
    }

    public Long modify(Long id, PostRequestDto dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        post.update(dto);

        postRepository.save(post);

        return post.getId();
    }

    public Long delete(Long id) {
        postRepository.deleteById(id);

        return id;
    }

}

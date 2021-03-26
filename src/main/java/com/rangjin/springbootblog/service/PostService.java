package com.rangjin.springbootblog.service;

import com.rangjin.springbootblog.domain.PageRequest;
import com.rangjin.springbootblog.domain.category.Category;
import com.rangjin.springbootblog.domain.category.CategoryRepository;
import com.rangjin.springbootblog.domain.post.Post;
import com.rangjin.springbootblog.domain.post.PostRepository;
import com.rangjin.springbootblog.domain.post.PostStatus;
import com.rangjin.springbootblog.web.dto.PostRequestDto;
import com.rangjin.springbootblog.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public Page<PostResponseDto> findAll(PageRequest pageRequest) {
        pageRequest.set(pageRequest.getPage(), 10, Sort.Direction.DESC, "updatedAt");

        return postRepository.findAll(pageRequest.of()).map(PostResponseDto::new);
    }

    public Page<PostResponseDto> findByStatus(PageRequest pageRequest) {
        pageRequest.set(pageRequest.getPage(), 10, Sort.Direction.DESC, "updatedAt");

        return postRepository.findByStatus(PostStatus.Public, pageRequest.of()).map(PostResponseDto::new);
    }

    public Page<PostResponseDto> findByCategory(Long id, PageRequest pageRequest) {
        pageRequest.set(pageRequest.getPage(), 10, Sort.Direction.DESC, "updatedAt");
        Category category = categoryRepository.findById(id).orElseThrow(RuntimeException::new);

        return postRepository.findByCategory(category, pageRequest.of()).map(PostResponseDto::new);
    }

    public Page<PostResponseDto> findByStatusAndCategory(Long id, PageRequest pageRequest) {
        pageRequest.set(pageRequest.getPage(), 10, Sort.Direction.DESC, "updatedAt");
        Category category = categoryRepository.findById(id).orElseThrow(RuntimeException::new);

        return postRepository.findByStatusAndCategory(PostStatus.Public, category, pageRequest.of()).map(PostResponseDto::new);
    }

    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(RuntimeException::new);

        return new PostResponseDto(post);
    }

    public PostResponseDto create(PostRequestDto dto) {
        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .status(dto.getStatus())
                .category(categoryRepository.findById(dto.getCategoryId()).orElse(null))
                .build();

        return new PostResponseDto(postRepository.save(post));
    }

    public PostResponseDto modify(Long id, PostRequestDto dto) {
        Post post = postRepository.findById(id).orElseThrow(RuntimeException::new);
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(RuntimeException::new);

        post.update(dto, category);

        return new PostResponseDto(postRepository.save(post));
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

}

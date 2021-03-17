package com.rangjin.springbootblog.domain.post;

import com.rangjin.springbootblog.domain.BaseTimeEntity;
import com.rangjin.springbootblog.domain.category.Category;
import com.rangjin.springbootblog.web.dto.PostRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private PostStatus status;

    @ManyToOne
    private Category category;

    @Builder
    public Post(String title, String content, PostStatus status, Category category) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.category = category;
    }

    public void update(PostRequestDto dto, Category category) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.status = dto.getStatus();
        this.category = category;
    }

}

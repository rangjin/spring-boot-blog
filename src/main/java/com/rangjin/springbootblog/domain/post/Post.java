package com.rangjin.springbootblog.domain.post;

import com.rangjin.springbootblog.domain.BaseTimeEntity;
import com.rangjin.springbootblog.domain.category.Category;
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
    public Post(String title, String content, PostStatus status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public void update(String title, String content, PostStatus status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

}

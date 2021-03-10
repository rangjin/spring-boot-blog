package com.rangjin.springbootblog.domain.category;

import com.rangjin.springbootblog.domain.BaseTimeEntity;
import com.rangjin.springbootblog.domain.post.Post;
import com.rangjin.springbootblog.web.dto.CategoryRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> postList;

    @Builder
    public Category(String name) {
        this.name = name;
    }

    public void update(CategoryRequestDto dto) {
        this.name = dto.getName();
    }

}

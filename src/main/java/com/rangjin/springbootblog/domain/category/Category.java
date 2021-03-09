package com.rangjin.springbootblog.domain.category;

import com.rangjin.springbootblog.domain.BaseTimeEntity;
import com.rangjin.springbootblog.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Accessors(chain = true)
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

    public void update(String name) {
        this.name = name;
    }

}

package com.dubbi.blogplatform.post.domain.vo;

import com.dubbi.blogplatform.post.domain.entity.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PostCategoryVo {
    private Long id;
    private String categoryName;
    private PostCategoryVo parentCategory;

    public PostCategoryVo(PostCategory postCategory){
        id = postCategory.getId();
        categoryName = postCategory.getCategoryName();
        parentCategory = postCategory.getChild() != null ? new PostCategoryVo(postCategory.getChild()) : null ;
    }
}

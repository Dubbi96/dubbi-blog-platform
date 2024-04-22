package com.dubbi.blogplatform.post.domain.vo;

import com.dubbi.blogplatform.post.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class PostVo {
    private Long id;
    private String title;
    private long views;
    private LocalDateTime createTs;
    private PostCategoryVo postCategoryId;
    private List<PostImageVo> postImages;

    public PostVo(Post post){
        id = post.getId();
        title = post.getTitle();
        views = post.getViews();
        createTs = post.getCreateTs();
        postCategoryId = new PostCategoryVo(post.getPostCategory());
        postImages = post.getPostImage().stream()
                .map(PostImageVo::new)
                .toList();
    }
}

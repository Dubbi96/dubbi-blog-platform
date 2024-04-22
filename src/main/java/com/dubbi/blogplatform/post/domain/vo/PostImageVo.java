package com.dubbi.blogplatform.post.domain.vo;

import com.dubbi.blogplatform.imageserver.domain.vo.ImageVo;
import com.dubbi.blogplatform.post.domain.entity.PostImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class PostImageVo {
    private long id;
    private ImageVo image;
    private long sequence;

    public PostImageVo(PostImage postImage) {
        id = postImage.getId();
        image = new ImageVo(postImage.getImage());
        sequence = postImage.getSequence();
    }
}

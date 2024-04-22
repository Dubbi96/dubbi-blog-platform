package com.dubbi.blogplatform.oneliner.domain.vo;

import com.dubbi.blogplatform.imageserver.domain.vo.ImageVo;
import com.dubbi.blogplatform.oneliner.domain.entity.OnelinerImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class OnelinerImageVo {
    private long id;
    private ImageVo image;
    private long sequence;

    public OnelinerImageVo(OnelinerImage onelinerImage) {
        id = onelinerImage.getId();
        image = new ImageVo(onelinerImage.getImage());
        sequence = onelinerImage.getSequence();
    }
}

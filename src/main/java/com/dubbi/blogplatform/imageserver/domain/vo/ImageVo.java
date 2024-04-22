package com.dubbi.blogplatform.imageserver.domain.vo;

import com.dubbi.blogplatform.imageserver.domain.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class ImageVo {
    private Long id;
    private String fileName;
    private String imagePrompt;
    private String url;
    private String contentType;

    public ImageVo(Image image){
        id = image.getId();
        fileName = image.getFileName();
        imagePrompt = image.getImagePrompt();
        url = image.getUrl();
        contentType = image.getContentType();
    }
}

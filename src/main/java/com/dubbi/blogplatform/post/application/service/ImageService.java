package com.dubbi.blogplatform.post.application.service;

import com.dubbi.blogplatform.post.domain.entity.Image;
import org.springframework.core.io.Resource;

public interface ImageService {
    Image getImageByUrl(String url);
    Resource loadFileAsResource(String fileName);
}

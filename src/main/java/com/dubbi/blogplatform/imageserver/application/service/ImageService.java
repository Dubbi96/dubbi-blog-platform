package com.dubbi.blogplatform.imageserver.application.service;

import com.dubbi.blogplatform.imageserver.domain.entity.Image;
import org.springframework.core.io.Resource;

public interface ImageService {
    Image getImageByUrl(String url);
    Resource loadFileAsResource(String fileName);
}

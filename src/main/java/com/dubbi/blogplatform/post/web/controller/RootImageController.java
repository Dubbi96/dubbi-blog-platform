package com.dubbi.blogplatform.post.web.controller;

import com.dubbi.blogplatform.post.application.service.ImageService;
import com.dubbi.blogplatform.post.domain.entity.Image;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class RootImageController {
    private final ImageService imageService;

    @GetMapping("/url")
    public ResponseEntity<Resource> getImageByUrl(@RequestPart String url, HttpServletRequest request) {
        Image image = imageService.getImageByUrl(url);
        Resource resource = imageService.loadFileAsResource(image.getFileName());
        String contentType = determineContentType(resource, request);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource)
                .body(resource);
    }

    private String determineContentType(Resource resource, HttpServletRequest request) {
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }
}

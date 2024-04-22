package com.dubbi.blogplatform.imageserver.application.service.implementation;

import com.dubbi.blogplatform.imageserver.application.service.ImageService;
import com.dubbi.blogplatform.imageserver.domain.entity.Image;
import com.dubbi.blogplatform.imageserver.domain.repository.ImageRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Value("${app.file-storage-location}")
    private String fileStorageLocation;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Path.of(fileStorageLocation));
    }
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = resolveNormalizedFilePath(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if(!resource.exists() && !resource.isReadable()) {
                throw new FileNotFoundException(String.format("Could not read file: %s", fileName));
            }
            return resource;
        } catch (FileNotFoundException | MalformedURLException e){
            return loadDefaultResource();
        }
    }

    private Resource loadDefaultResource() {
        try {
            Path filePath = resolveNormalizedFilePath("notfound");
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Could not load 'not found' image", e);
        }
    }

    private Path resolveNormalizedFilePath(String fileName) {
        return Paths.get(fileStorageLocation).resolve(fileName).normalize();
    }
    public Image getImageByUrl(String url){
        return imageRepository.findByUrl(url).orElseThrow(() -> new RuntimeException("Image not found with url" + url ));
    }
}

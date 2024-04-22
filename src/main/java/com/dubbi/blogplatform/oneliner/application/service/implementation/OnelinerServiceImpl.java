package com.dubbi.blogplatform.oneliner.application.service.implementation;

import com.dubbi.blogplatform.authentication.domain.entity.User;
import com.dubbi.blogplatform.imageserver.domain.entity.Image;
import com.dubbi.blogplatform.imageserver.domain.repository.ImageRepository;
import com.dubbi.blogplatform.oneliner.application.dto.CreateOnelinerDto;
import com.dubbi.blogplatform.oneliner.application.service.OnelinerService;
import com.dubbi.blogplatform.oneliner.domain.entity.Oneliner;
import com.dubbi.blogplatform.oneliner.domain.entity.OnelinerImage;
import com.dubbi.blogplatform.oneliner.domain.repository.OnelinerImageRepository;
import com.dubbi.blogplatform.oneliner.domain.repository.OnelinerRepository;
import com.dubbi.blogplatform.oneliner.domain.vo.OnelinerVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OnelinerServiceImpl implements OnelinerService {
    private final OnelinerRepository onelinerRepository;
    private final OnelinerImageRepository onelinerImageRepository;
    private final ImageRepository imageRepository;
    private final TransactionalOperator transactionalOperator;

    @Value("${app.file-storage-location}")
    private String fileStorageLocation;

    @Override
    @Transactional
    public OnelinerVo createOneliner(CreateOnelinerDto createOnelinerDto) {
        try{
            Oneliner oneliner = Oneliner.builder()
                .creator(getUserFromContextHolder())
                .content(createOnelinerDto.getContent())
                .point(calculatePoint(createOnelinerDto.getLatitude(), createOnelinerDto.getLongitude()))
                .build();
            Oneliner newOneliner = onelinerRepository.save(oneliner);
            long imageSeq = 0L;
            for(MultipartFile file : createOnelinerDto.getImages()) {
                Image storedImage = storeImage(file);
                OnelinerImage onelinerImage = OnelinerImage.builder()
                        .image(storedImage)
                        .oneliner(newOneliner)
                        .sequence(imageSeq++).build();
                onelinerImageRepository.save(onelinerImage);
            }
            return new OnelinerVo(newOneliner);
        }
        catch (ParseException e){
            log.error("위치 정보가 부정확하게 입력되었습니다! 다시 시도해주세요.{}", e);
            return new OnelinerVo();
        }
    }

    private Image storeImage(MultipartFile file){
        String fileName = storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();
        Image image = Image.builder()
                .fileName(fileName)
                .url(fileDownloadUri)
                .imagePrompt(null)
                .contentType(file.getContentType()).build();
        return imageRepository.save(image);
    }
    private String storeFile(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (originalFileName.contains("..")) {
                throw new IllegalArgumentException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }
            String fileName = UUID.randomUUID() + "_" + originalFileName; // Ensure the file name is unique
            Path targetLocation = Paths.get(fileStorageLocation).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not store file " + originalFileName + ". Please try again!", e);
        }
    }
    private User getUserFromContextHolder(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

    public Point calculatePoint(Double latitude, Double longitude) throws ParseException {
        return latitude != null && longitude != null ? (Point) new WKTReader().read(String.format("POINT(%s %s)",latitude,longitude)) : null;
    }
}

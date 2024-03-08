package com.dubbi.blogplatform.application.service.implementation;

import com.dubbi.blogplatform.application.dto.*;
import com.dubbi.blogplatform.application.service.JwtService;
import com.dubbi.blogplatform.application.service.PostService;
import com.dubbi.blogplatform.domain.entity.Post;
import com.dubbi.blogplatform.domain.entity.PostImage;
import com.dubbi.blogplatform.domain.entity.User;
import com.dubbi.blogplatform.domain.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Value("${imageserver.url}")
    private String IMAGE_SERVER_BASE_URL;

    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PostImageQueryRepository postImageQueryRepository;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public void createPost(String accessToken, CreatePostDto createPostDto) {
        Post post = Post.builder()
                .creator(findUserByAccessToken(accessToken))
                .title(createPostDto.getTitle())
                .content(createPostDto.getContent())
                .createTs(LocalDateTime.now())
                .isDeactivated(false)
                .postCategory(postCategoryRepository.findById(createPostDto.getPostCategoryId()).orElseThrow())
                .build();
        postRepository.save(post);
        for (MultipartFile file : createPostDto.getPostImage()) {
            try {
                String imageUrl = uploadImageToServer(file); // 이미지를 업로드하고 URL을 받음
            PostImage temporalPostImage = PostImage.builder()
                    .fileName(imageUrl) // URL을 저장
                    .post(post).build();
            postImageRepository.save(temporalPostImage);
            } catch (IOException e) {
                log.error("Cannot upload this type of file or image server is not responding", e);
            }
        }
    }

    private String uploadImageToServer(MultipartFile file) throws IOException {
        String url = IMAGE_SERVER_BASE_URL+"/images/upload";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Resource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        body.add("image", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<GetImageToServerResponseDto> response = restTemplate.postForEntity(url, requestEntity, GetImageToServerResponseDto.class);
        try{
            if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
                return response.getBody().getUrl(); // URL만 추출하여 반환
            } else {
                throw new IOException("Image upload failed with status: " + response.getStatusCode());
            }
            //Body가 null을 반환할 수 있으므로, 해당 경우 image server가 닫혀있다 판단하며, IOException으로 변환
        }catch (NullPointerException e){
            log.error("Image server is not responding : " + response.getStatusCode());
            throw new IOException("Image server is not responding : " + response.getStatusCode());
        }
    }

    @Override
    public List<GetAllPostDto> getAllPost(String accessToken) {
        List<GetAllPostDto> response = new ArrayList<>();
        for(Post post : postQueryRepository.findAllPost(findUserByAccessToken(accessToken))){
            GetAllPostDto tempPostDto = GetAllPostDto.builder()
                    .title(post.getTitle())
                    .creatorId(post.getCreator().getId())
                    .views(post.getView())
                    .createTs(post.getCreateTs())
                    .postCategoryId(post.getPostCategory().getId()).build();
            response.add(tempPostDto);
        }
        return response;
    }

    @Override
    public GetPostDto getPost(String accessToken, Long id) {
        Post post = postQueryRepository.findPost(findUserByAccessToken(accessToken),id);
        List<PostImage> postImage= postImageQueryRepository.findAllPostImage(id);
        List<String> postImageToString = new ArrayList<>();
        if(postImage.isEmpty()){
            return GetPostDto.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .views(post.getView())
                    .creatorId(post.getCreator().getId())
                    .build();
        }
        for(PostImage image : postImage){
            postImageToString.add(image.getFileName());
        }
        return GetPostDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .postImages(postImageToString)
                .views(post.getView())
                .creatorId(post.getCreator().getId())
                .build();
    }

    @Override
    @Transactional
    public void updatePostDetail(String accessToken, UpdatePostDetailDto updatePostDetailDto, Long id) {
        // 1. 기존 포스트 조회 및 업데이트
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        post.updateDetails(updatePostDetailDto.getTitle(), updatePostDetailDto.getContent());

        // 2. 이미지 삭제 처리
        if (updatePostDetailDto.getDeleteImageIds() != null) {
            for (Long imageId : updatePostDetailDto.getDeleteImageIds()) {
                postImageRepository.deleteById(imageId);
            }
        }

        // 3. 신규 이미지 추가 처리
        if (updatePostDetailDto.getNewPostImages() != null) {
            for (MultipartFile file : updatePostDetailDto.getNewPostImages()) {
                if (!file.isEmpty()) {
                    try {
                        String imageUrl = uploadImageToServer(file);
                        PostImage newImage = PostImage.builder().fileName(imageUrl).post(post).build();
                        postImageRepository.save(newImage);
                    } catch (IOException e) {
                        log.error("Failed to upload image", e);
                    }
                }
            }
        }

        // 4. 포스트 저장
        postRepository.save(post);
    }

    @Override
    public Long deactivatePost(String accessToken, Long postId) {
        postRepository.deactivatePostById(postId);
        return postId;
    }

    @Override
    public Long deletePost(String accessToken, Long postId) {
        postRepository.deletePostById(postId);
        return postId;
    }

    private User findUserByAccessToken(String accessToken){
        return userRepository.findByEmail(jwtService.extractEmail(accessToken).orElseThrow()).orElseThrow();
    }
}

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
                .is_deactivated(false)
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
                log.error("Cannot upload this type of file", e);
            }
        }
    }

    private String uploadImageToServer(MultipartFile file) throws IOException {
        String url = "http://3.39.254.153:9003/images/upload";
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
        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
            return response.getBody().getUrl(); // URL만 추출하여 반환
        } else {
            throw new IOException("Image upload failed with status: " + response.getStatusCode());
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
    public void updatePostDetail(String accessToken, UpdatePostDetailDto updatePostDetailDto, Long id) {
        Post post = Post.builder()
                .id(id)
                .creator(findUserByAccessToken(accessToken))
                .title(updatePostDetailDto.getTitle())
                .content(updatePostDetailDto.getContent())
                .createTs(LocalDateTime.now())
                .is_deactivated(false)
                .build();
        postRepository.save(post);
        if(!updatePostDetailDto.getPostImage().isEmpty()) {
            for (String postImage : updatePostDetailDto.getPostImage()) {
                PostImage temporalPostImage = PostImage.builder()
                        .fileName(postImage)
                        .post(post).build();
                postImageRepository.save(temporalPostImage);
            }
        }
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

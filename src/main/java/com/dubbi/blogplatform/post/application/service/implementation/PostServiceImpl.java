package com.dubbi.blogplatform.post.application.service.implementation;

import com.dubbi.blogplatform.authentication.domain.entity.User;
import com.dubbi.blogplatform.imageserver.domain.entity.Image;
import com.dubbi.blogplatform.imageserver.domain.repository.ImageRepository;
import com.dubbi.blogplatform.post.application.dto.CreatePostDto;
import com.dubbi.blogplatform.post.application.dto.GetAllPostDto;
import com.dubbi.blogplatform.post.application.dto.GetPostDto;
import com.dubbi.blogplatform.post.application.dto.UpdatePostDetailDto;
import com.dubbi.blogplatform.post.application.service.PostService;
import com.dubbi.blogplatform.post.domain.entity.Post;
import com.dubbi.blogplatform.post.domain.entity.PostImage;
import com.dubbi.blogplatform.post.domain.repository.*;
import com.dubbi.blogplatform.post.domain.vo.PostVo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    private final ImageRepository imageRepository;

    @Value("${app.file-storage-location}")
    private String fileStorageLocation;

    /**
     * Creates a new post with the given access token and post information.
     *
     * @param createPostDto the DTO containing the post title, content, post category, and post images
     */
    @Override
    @Transactional
    public PostVo createPost(CreatePostDto createPostDto) {
        Post post = Post.builder()
                .creator(getUserFromContextHolder())
                .title(createPostDto.getTitle())
                .content(createPostDto.getContent())
                .isDeactivated(false)
                .views(0L)
                .postCategory(postCategoryRepository.findById(createPostDto.getPostCategoryId()).orElseThrow())
                .build();
        Post newPost = postRepository.save(post);
        long imageSeq = 0;
        for (MultipartFile file : createPostDto.getPostImages()) {
            Image storedImage = storeImage(file); //이미지를 업로드 하고 Image 엔티티를 반환, 이때 ImageRepository에 우선 저장
            PostImage newPostImage = PostImage.builder()
                    .image(storedImage)
                    .post(newPost)
                    .sequence(imageSeq++).build();
            postImageRepository.save(newPostImage);
        }
        return new PostVo(newPost);
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

    @Override
    public List<GetAllPostDto> getAllPost() {
        List<GetAllPostDto> response = new ArrayList<>();
        for(Post post : postQueryRepository.findAllPost(getUserFromContextHolder())){
            GetAllPostDto tempPostDto = GetAllPostDto.builder()
                    .title(post.getTitle())
                    .creatorId(post.getCreator() != null ? post.getCreator().getId() : null)
                    .views(post.getViews())
                    .createTs(post.getCreateTs())
                    .postCategoryId(post.getPostCategory().getId()).build();
            response.add(tempPostDto);
        }
        return response;
    }

    //이 방식이 query수가 더 적어 getAllPost()를 대체할 예정
    @Override
    public List<PostVo> getAllPostWithCollectionDtos() {
        List<Post> posts = postQueryRepository.findAllPostsWithDetails(getUserFromContextHolder());
        log.error("start context");
        List<PostVo> response = new ArrayList<>();
        for(Post post : posts){
            response.add(new PostVo(post));
        }
        return response;
    }

    @Override
    public GetPostDto getPost(Long id) {
        Post post = postQueryRepository.findPost(getUserFromContextHolder(), id).orElseThrow(EntityNotFoundException::new);
        List<PostImage> postImage= postImageQueryRepository.findAllPostImage(id);
        List<String> postImageToString = new ArrayList<>();
        if(postImage.isEmpty()){
            return GetPostDto.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .views(post.getViews())
                    .creatorId(post.getCreator().getId())
                    .build();
        }
        for(PostImage image : postImage){
            postImageToString.add(image.getImage().getUrl());
        }
        return GetPostDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .postImages(postImageToString)
                .views(post.getViews())
                .creatorId(post.getCreator().getId())
                .build();
    }

    @Override
    @Transactional
    public PostVo updatePostDetail(UpdatePostDetailDto updatePostDetailDto, Long id) {
        // 1. 기존 포스트 조회 및 업데이트
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("Post with id " + id + " does not exist");
        }
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
            long imageSeq = 0;
            for (MultipartFile file : updatePostDetailDto.getNewPostImages()) {
                if (!file.isEmpty()) {
                    Image storedImage = storeImage(file); //이미지를 업로드 하고 Image 엔티티를 반환, 이때 ImageRepository에 우선 저장
                    PostImage newPostImage = PostImage.builder()
                            .image(storedImage)
                            .post(post)
                            .sequence(imageSeq++).build();
                    postImageRepository.save(newPostImage);
                }
            }
        }
        // 4. 포스트 저장
        Post updatedPost = postRepository.save(post);
        return new PostVo(updatedPost);
    }

    @Override
    public Long deactivatePost(Long postId) {
        postRepository.deactivatePostById(postId);
        return postId;
    }

    @Override
    public Long deletePost(Long postId) {
        postRepository.deletePostById(postId);
        return postId;
    }

    private User getUserFromContextHolder(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

}

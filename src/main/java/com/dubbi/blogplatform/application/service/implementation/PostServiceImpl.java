package com.dubbi.blogplatform.application.service.implementation;

import com.dubbi.blogplatform.application.dto.CreatePostDto;
import com.dubbi.blogplatform.application.dto.GetAllPostDto;
import com.dubbi.blogplatform.application.dto.GetPostDto;
import com.dubbi.blogplatform.application.dto.UpdatePostDetailDto;
import com.dubbi.blogplatform.application.service.JwtService;
import com.dubbi.blogplatform.application.service.PostService;
import com.dubbi.blogplatform.domain.entity.Post;
import com.dubbi.blogplatform.domain.entity.PostImage;
import com.dubbi.blogplatform.domain.entity.User;
import com.dubbi.blogplatform.domain.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PostImageQueryRepository postImageQueryRepository;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

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
        if(!createPostDto.getPostImage().isEmpty()) {
            for (String postImage : createPostDto.getPostImage()) {
                PostImage temporalPostImage = PostImage.builder()
                        .file_name(postImage)
                        .post(post).build();
                postImageRepository.save(temporalPostImage);
            }
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
            postImageToString.add(image.getFile_name());
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
                        .file_name(postImage)
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

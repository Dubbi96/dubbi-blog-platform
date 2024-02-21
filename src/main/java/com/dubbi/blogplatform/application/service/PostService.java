package com.dubbi.blogplatform.application.service;

import com.dubbi.blogplatform.application.dto.CreatePostDto;
import com.dubbi.blogplatform.application.dto.GetAllPostDto;
import com.dubbi.blogplatform.application.dto.GetPostDto;
import com.dubbi.blogplatform.application.dto.UpdatePostDto;
import com.dubbi.blogplatform.aspect.AccessTokenUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    void createPost(String accessToken, CreatePostDto createPostDto);

    List<GetAllPostDto> getAllPost(String accessToken);

    GetPostDto getPost(String accessToken, Long id);

    void updatePost(String accessToken, UpdatePostDto updatePostDto);

    Long deactivatePost(String accessToken, Long postId);

    Long deletePost(String accessToken, Long postId);

}

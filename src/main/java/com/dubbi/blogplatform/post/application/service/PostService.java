package com.dubbi.blogplatform.post.application.service;

import com.dubbi.blogplatform.authentication.application.dto.dto.CreatePostDto;
import com.dubbi.blogplatform.authentication.application.dto.dto.GetAllPostDto;
import com.dubbi.blogplatform.authentication.application.dto.dto.GetPostDto;
import com.dubbi.blogplatform.authentication.application.dto.dto.UpdatePostDetailDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    void createPost(CreatePostDto createPostDto);

    List<GetAllPostDto> getAllPost();

    GetPostDto getPost(Long id);

    void updatePostDetail(UpdatePostDetailDto updatePostDetailDto, Long id);

    Long deactivatePost(Long postId);

    Long deletePost(Long postId);

}

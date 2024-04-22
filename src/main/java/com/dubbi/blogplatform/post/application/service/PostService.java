package com.dubbi.blogplatform.post.application.service;

import com.dubbi.blogplatform.post.application.dto.*;
import com.dubbi.blogplatform.post.domain.vo.PostVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostVo createPost(CreatePostDto createPostDto);

    List<GetAllPostDto> getAllPost();

    /**테스트를 위한 임시 기능입니다 <<- 앞으로 정규 기능입니다.*/
    List<PostVo> getAllPostWithCollectionDtos();

    GetPostDto getPost(Long id);

    PostVo updatePostDetail(UpdatePostDetailDto updatePostDetailDto, Long id);

    Long deactivatePost(Long postId);

    Long deletePost(Long postId);

}

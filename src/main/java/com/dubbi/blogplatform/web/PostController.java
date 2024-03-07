package com.dubbi.blogplatform.web;

import com.dubbi.blogplatform.application.dto.CreatePostDto;
import com.dubbi.blogplatform.application.dto.GetAllPostDto;
import com.dubbi.blogplatform.application.dto.GetPostDto;
import com.dubbi.blogplatform.application.dto.UpdatePostDetailDto;
import com.dubbi.blogplatform.application.service.PostService;
import com.dubbi.blogplatform.aspect.AccessTokenUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<String> createPost(
            @AccessTokenUser String accessToken,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("postCategoryId") Long postCategoryId,
            @RequestParam("postImage") MultipartFile[] postImage) {
        CreatePostDto createPostDto = new CreatePostDto(title, content, postImage, postCategoryId);
        postService.createPost(accessToken, createPostDto);
        return ResponseEntity.ok("Post saved");
    }

    @GetMapping("/post")
    public ResponseEntity<List<GetAllPostDto>> getAllPost(@AccessTokenUser String accessToken){
        List<GetAllPostDto> posts = postService.getAllPost(accessToken);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<GetPostDto> getPost(@AccessTokenUser String accessToken, @PathVariable Long id){
        return ResponseEntity.ok(postService.getPost(accessToken,id));
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<String> updatePostDetail(@AccessTokenUser String accessToken,
                                                   @RequestParam("title") String title,
                                                   @RequestParam("content") String content,
                                                   @RequestParam("deletedImageIds") List<Long> deleteImageId,
                                                   @RequestParam("newPostImages") List<MultipartFile> newPostImages,
                                                   @PathVariable Long id){
        UpdatePostDetailDto updatePostDetailDto = new UpdatePostDetailDto(title,content,deleteImageId,newPostImages);
        postService.updatePostDetail(accessToken,updatePostDetailDto,id);
        return ResponseEntity.ok("Post successfully updated");
    }

    @PatchMapping("/post/{id}")
    public ResponseEntity<Long> deactivatePost(@AccessTokenUser String accessToken, @PathVariable Long id){
        return ResponseEntity.ok(postService.deactivatePost(accessToken,id));
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Long> deletePost(@AccessTokenUser String accessToken, @PathVariable Long id){
        return ResponseEntity.ok(postService.deletePost(accessToken,id));
    }
}

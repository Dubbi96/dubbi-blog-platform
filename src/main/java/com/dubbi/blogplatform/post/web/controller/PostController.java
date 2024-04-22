package com.dubbi.blogplatform.post.web.controller;

import com.dubbi.blogplatform.post.application.dto.*;
import com.dubbi.blogplatform.post.application.service.PostService;
import com.dubbi.blogplatform.post.domain.vo.PostVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<PostVo> createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("postCategoryId") Long postCategoryId,
            @RequestParam("postImage") MultipartFile[] postImage) {
        CreatePostDto createPostDto = new CreatePostDto(title, content, postImage, postCategoryId);
        PostVo savedPost = postService.createPost(createPostDto);
        return ResponseEntity.ok(savedPost);
    }

    @GetMapping("/post")
    public ResponseEntity<List<GetAllPostDto>> getAllPost(){
        List<GetAllPostDto> posts = postService.getAllPost();
        return ResponseEntity.ok(posts);}

    @GetMapping("/get-all-post")
    public ResponseEntity<List<PostVo>> getAllPostNew(){
        List<PostVo> posts = postService.getAllPostWithCollectionDtos();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<GetPostDto> getPost(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<PostVo> updatePostDetail(@RequestParam("title") String title,
                                                   @RequestParam("content") String content,
                                                   @RequestParam("deletedImageIds") List<Long> deleteImageId,
                                                   @RequestParam("newPostImages") List<MultipartFile> newPostImages,
                                                   @PathVariable Long id){
        UpdatePostDetailDto updatePostDetailDto = new UpdatePostDetailDto(title,content,deleteImageId,newPostImages);
        PostVo updatedPost = postService.updatePostDetail(updatePostDetailDto,id);
        return ResponseEntity.ok(updatedPost);
    }

    @PatchMapping("/post/{id}")
    public ResponseEntity<Long> deactivatePost(@PathVariable Long id){
        return ResponseEntity.ok(postService.deactivatePost(id));
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Long> deletePost(@PathVariable Long id){
        return ResponseEntity.ok(postService.deletePost(id));
    }
}

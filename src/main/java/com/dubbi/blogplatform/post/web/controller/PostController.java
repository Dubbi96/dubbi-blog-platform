package com.dubbi.blogplatform.post.web.controller;

import com.dubbi.blogplatform.authentication.application.dto.dto.CreatePostDto;
import com.dubbi.blogplatform.authentication.application.dto.dto.GetAllPostDto;
import com.dubbi.blogplatform.authentication.application.dto.dto.GetPostDto;
import com.dubbi.blogplatform.authentication.application.dto.dto.UpdatePostDetailDto;
import com.dubbi.blogplatform.post.application.service.PostService;
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
    public ResponseEntity<String> createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("postCategoryId") Long postCategoryId,
            @RequestParam("postImage") MultipartFile[] postImage) {
        CreatePostDto createPostDto = new CreatePostDto(title, content, postImage, postCategoryId);
        postService.createPost(createPostDto);
        return ResponseEntity.ok("Post saved");
    }

    @GetMapping("/post")
    public ResponseEntity<List<GetAllPostDto>> getAllPost(){
        List<GetAllPostDto> posts = postService.getAllPost();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<GetPostDto> getPost(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<String> updatePostDetail(@RequestParam("title") String title,
                                                   @RequestParam("content") String content,
                                                   @RequestParam("deletedImageIds") List<Long> deleteImageId,
                                                   @RequestParam("newPostImages") List<MultipartFile> newPostImages,
                                                   @PathVariable Long id){
        UpdatePostDetailDto updatePostDetailDto = new UpdatePostDetailDto(title,content,deleteImageId,newPostImages);
        postService.updatePostDetail(updatePostDetailDto,id);
        return ResponseEntity.ok("Post successfully updated");
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

package com.dubbi.blogplatform.web;

import com.dubbi.blogplatform.application.dto.CreatePostDto;
import com.dubbi.blogplatform.application.dto.GetAllPostDto;
import com.dubbi.blogplatform.application.service.PostService;
import com.dubbi.blogplatform.aspect.AccessTokenUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<String> createPost(@AccessTokenUser String accessToken, @RequestBody CreatePostDto createPostDto){
        postService.createPost(accessToken,createPostDto);
        return ResponseEntity.ok("Post saved");
    }

    @GetMapping("/post")
    public ResponseEntity<List<GetAllPostDto>> getAllPost(@AccessTokenUser String accessToken){
        List<GetAllPostDto> posts = postService.getAllPost(accessToken);
        return ResponseEntity.ok(posts);
    }
}

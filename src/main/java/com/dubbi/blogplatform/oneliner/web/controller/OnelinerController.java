package com.dubbi.blogplatform.oneliner.web.controller;

import com.dubbi.blogplatform.oneliner.application.dto.CreateOnelinerDto;
import com.dubbi.blogplatform.oneliner.application.service.OnelinerService;
import com.dubbi.blogplatform.oneliner.domain.vo.OnelinerVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OnelinerController {
    private final OnelinerService onelinerService;

    @PostMapping("/oneliner")
    public ResponseEntity<OnelinerVo> createOneliner(@RequestParam("content") String content,
                                                     @RequestParam("onelinerImage") MultipartFile[] onelinerImage){
        CreateOnelinerDto createOnelinerDto = new CreateOnelinerDto(content, onelinerImage);
        OnelinerVo savedOneliner = onelinerService.createOneliner(createOnelinerDto);
        return ResponseEntity.ok().body(savedOneliner);
    }
}

package com.yagodda.controllers;

import com.yagodda.dto.PostDTO;
import com.yagodda.models.Post;
import com.yagodda.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

//    @GetMapping
//    public ResponseEntity<List<PostDTO>> posts() {
//        List<PostDTO> postDTOList = postService.getAllPosts()
//                .stream()
//                .map(this::conventToDTO)
//                .collect(Collectors.toList());
//        return null;
//    }
//
//    private PostDTO conventToDTO(Post post) {
//        PostDTO
//    }

}

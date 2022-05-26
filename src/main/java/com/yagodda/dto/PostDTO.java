package com.yagodda.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class PostDTO {
    private Long id;
    private String caption;
    private Long likes;
    private List<String> images;
    private UserDTO userDTO;
}

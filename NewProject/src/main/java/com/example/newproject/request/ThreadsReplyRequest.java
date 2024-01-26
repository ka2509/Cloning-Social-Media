package com.example.newproject.request;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ThreadsReplyRequest {
    private String content;
    private Long threadID;
    private LocalDateTime createdAt;
    private String image;
    private String video;
}

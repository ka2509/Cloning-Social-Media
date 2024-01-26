package com.example.newproject.controller;

import com.example.newproject.service.ThreadsService;
import com.example.newproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/threads")
public class ThreadsController {
    @Autowired
    private ThreadsService threadsService;

    @Autowired
    private UserService userService;
}

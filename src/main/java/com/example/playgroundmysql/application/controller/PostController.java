package com.example.playgroundmysql.application.controller;

import com.example.playgroundmysql.domain.post.dto.DailyPostCount;
import com.example.playgroundmysql.domain.post.dto.DailyPostCountRequest;
import com.example.playgroundmysql.domain.post.dto.PostCommand;
import com.example.playgroundmysql.domain.post.service.PostReadService;
import com.example.playgroundmysql.domain.post.service.PostWriteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostWriteService postWriteService;

    private final PostReadService postReadService;

    @PostMapping("")
    public Long create(PostCommand command){
        return postWriteService.create(command);
    }
    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(@RequestBody DailyPostCountRequest request){
        return postReadService.getDailyPostCount(request);
    }
}

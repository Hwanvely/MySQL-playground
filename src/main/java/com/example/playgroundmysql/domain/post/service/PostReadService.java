package com.example.playgroundmysql.domain.post.service;

import com.example.playgroundmysql.domain.post.dto.DailyPostCount;
import com.example.playgroundmysql.domain.post.dto.DailyPostCountRequest;
import com.example.playgroundmysql.domain.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostReadService {
    private final PostRepository postRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request){
        /*
            반환 값 -> 리스트 [작성일자, 작성회원, 작성 개시물 갯수]
            select createdDate, memberId, count(id)
            from Post
            where memberId =: memberId and createdDate between firstDate and lastDate
            group by createdDate memberId
         */
        return postRepository.groupByCreatedDate(request);
    }
}

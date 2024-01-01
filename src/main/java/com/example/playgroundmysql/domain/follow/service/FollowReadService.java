package com.example.playgroundmysql.domain.follow.service;

import com.example.playgroundmysql.domain.follow.entity.Follow;
import com.example.playgroundmysql.domain.follow.repository.FollowRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowReadService {

    private final FollowRepository followRepository;

    public List<Follow> getFollowings(Long memberId){
        return followRepository.findAllByFromMemberId(memberId);
    }

}

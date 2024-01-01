package com.example.playgroundmysql.domain.follow.service;

import com.example.playgroundmysql.domain.follow.entity.Follow;
import com.example.playgroundmysql.domain.follow.repository.FollowRepository;
import com.example.playgroundmysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
@RequiredArgsConstructor
@Service
public class FollowWriteService {

    private final FollowRepository followRepository;

    public void create(MemberDto fromMember, MemberDto toMember){

        Assert.isTrue(!fromMember.id().equals(toMember.id()),
                "From, To 회원이 동일 합니다.");
        Follow follow = Follow.builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .build();

        followRepository.save(follow);
    }
}

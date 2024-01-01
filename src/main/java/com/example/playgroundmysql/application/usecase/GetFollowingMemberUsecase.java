package com.example.playgroundmysql.application.usecase;

import com.example.playgroundmysql.domain.follow.entity.Follow;
import com.example.playgroundmysql.domain.follow.service.FollowReadService;
import com.example.playgroundmysql.domain.member.dto.MemberDto;
import com.example.playgroundmysql.domain.member.service.MemberReadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetFollowingMemberUsecase {

    private final MemberReadService memberReadService;

    private final FollowReadService followReadService;

    public List<MemberDto> execute(Long memberId){

        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return memberReadService.getMembers(followingMemberIds);
    }
}

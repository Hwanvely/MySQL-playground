package com.example.playgroundmysql.application.usecase;

import com.example.playgroundmysql.domain.follow.service.FollowWriteService;
import com.example.playgroundmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateFollowMemberUsecase {

    private final MemberReadService memberReadService; //read, write를 분리한 덕분에 쓰기 권한이 없다. --> 의존성

    private final FollowWriteService followWriteService;

    /*
        서로 다른 도메인간의 데이터를 주고 받아야 할때 사용할만한 오케스트레이션
        비지니스 로직이 없어야한다
        단순히 흐름만 제어 역할
     */
    public void execute(Long fromMemberId, Long toMemberId ){
        /*
            1. 입력받은 memberId로 회원조회
            2. FollowWriteService.create()
         */
        var fromMember = memberReadService.getMember(fromMemberId);
        var toMember = memberReadService.getMember(toMemberId);

        followWriteService.create(fromMember, toMember);
    }
}

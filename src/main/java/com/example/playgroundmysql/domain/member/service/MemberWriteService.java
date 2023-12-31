package com.example.playgroundmysql.domain.member.service;

import com.example.playgroundmysql.domain.member.dto.RegisterMemberCommand;
import com.example.playgroundmysql.domain.member.entity.Member;
import com.example.playgroundmysql.domain.member.entity.MemberNicknameHistory;
import com.example.playgroundmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.playgroundmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService {

    private final MemberRepository memberRepository;

    private final MemberNicknameHistoryRepository historyRepository;

    public Member register(RegisterMemberCommand command){
        /*
            GOAL - 회원정보(이메일 ,닉네임, 생년월일)을 등록한다.
                 - 닉네임은 10자를 넘길 수 없다.
            파라미터 - memberRegisterCommand
            val member = Member.of(memberRegisterCommand)
            memberRepository.save(member)
         */
        Member member = Member.builder()
                .nickname(command.nickname())
                .birthDay(command.birthDay())
                .email(command.email())
                .build();
        var savedMember =  memberRepository.save(member);
        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    public void changeNickname(Long memberId, String nickname){
        /*
            1. 회원의 이름을 변경
            2. 변경 내역을 저장한다.
         */
        var member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);

        saveMemberNicknameHistory(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        var history = MemberNicknameHistory.builder()
                        .memberId(member.getId())
                        .nickname(member.getNickname())
                        .createdAt(member.getCreatedAt())
                        .build();

        historyRepository.save(history);
    }


}

package com.example.playgroundmysql.domain.member.service;

import com.example.playgroundmysql.domain.member.dto.MemberDto;
import com.example.playgroundmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.playgroundmysql.domain.member.entity.Member;
import com.example.playgroundmysql.domain.member.entity.MemberNicknameHistory;
import com.example.playgroundmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.playgroundmysql.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberReadService {
    private final MemberRepository memberRepository;

    private final MemberNicknameHistoryRepository historyRepository;

    public MemberDto getMember(Long id){
        var member = memberRepository.findById(id).orElseThrow();
        return toDto(member);
    }

    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId){
        return historyRepository
                .findAllByMemberId(memberId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public MemberDto toDto(Member member){
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthDay());
    }

    private MemberNicknameHistoryDto toDto(MemberNicknameHistory history){
        return new MemberNicknameHistoryDto(
                history.getId(),
                history.getMemberId(),
                history.getNickname(),
                history.getCreatedAt()
        );
    }
}

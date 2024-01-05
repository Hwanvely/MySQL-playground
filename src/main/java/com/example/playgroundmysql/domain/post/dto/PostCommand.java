package com.example.playgroundmysql.domain.post.dto;

public record PostCommand(
        Long memberId,
        String contents
) {
}

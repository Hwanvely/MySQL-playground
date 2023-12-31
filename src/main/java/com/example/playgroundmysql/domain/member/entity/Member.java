package com.example.playgroundmysql.domain.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Member {
    final private Long id;

    final private String email;

    private String nickname;

    final private LocalDate birthDay;

    final private LocalDateTime createdAt; // 디버깅할때 상당히 유용하므로 생성시간을 만들어준다

    final private static Long NAME_MAX_LENGTH = 10L;

    // 필드끼리 의존성이 없이 다 입력을 받아야 하므로 다 열어준다
    @Builder
    public Member(Long id, String email, String nickname, LocalDate birthDay, LocalDateTime createdAt) {
        this.id = id; //후에 JPA를 사용하기 위하여 nullable을 열어준다 --> jpa는 id값에 따라 insert냐 update냐를 결정
        this.email = Objects.requireNonNull(email);
        this.birthDay = Objects.requireNonNull(birthDay);

        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);

        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt; //삼항연산자 왜?
    }

    public void changeNickname(String to){
        Objects.requireNonNull(to);
        validateNickname(to);
        nickname = to;
    }
    private void validateNickname(String nickname){
        // todo :추후에 customException 만들어보자
        Assert.isTrue(nickname.length() <= NAME_MAX_LENGTH, "최대 길이를 초과했습니다");

    }
}

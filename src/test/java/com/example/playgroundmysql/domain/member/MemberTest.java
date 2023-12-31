package com.example.playgroundmysql.domain.member;

import com.example.playgroundmysql.util.MemberFixtureFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다")
    @Test
    public void testChangeName(){
        /**LongStream.range(0,10)
                .mapToObj(MemberFixtureFactory::create)
                .forEach(member -> {
                    System.out.println(member.getNickname());
                });
         */
        var member = MemberFixtureFactory.create();
        var expected = "hwan";

        member.changeNickname(expected);
        Assertions.assertEquals(expected, member.getNickname());
    }

    @DisplayName("회원은 닉네임은 10자를 초과할 수 없다")
    @Test
    public void testNicknameMaxLength(){
        var member = MemberFixtureFactory.create();
        var overMaxLenghtName = "hwanhwanhwan";

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> member.changeNickname(overMaxLenghtName));

    }
}
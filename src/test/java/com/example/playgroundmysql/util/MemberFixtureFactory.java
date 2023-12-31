package com.example.playgroundmysql.util;

import com.example.playgroundmysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class MemberFixtureFactory {

    //objectMother 패턴
    public static Member create(Long seed){
        var param = new EasyRandomParameters()
                .seed(seed);
        return new EasyRandom(param).nextObject(Member.class);
    }

    public static Member create(){
        var param = new EasyRandomParameters();
        return new EasyRandom(param).nextObject(Member.class);
    }
}

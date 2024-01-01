package com.example.playgroundmysql.domain.member.repository;

import com.example.playgroundmysql.domain.member.entity.Member;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    private static final String TABLE = "member";

    private static final RowMapper<Member> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Member
            .builder()
            .id(resultSet.getLong("id"))
            .email(resultSet.getString("email"))
            .nickname(resultSet.getString("nickname"))
            .birthDay(resultSet.getObject("birthday", LocalDate.class))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    public Optional<Member> findById(Long id){
        /*
            select *
            from member
            where id = :id
         */
        var sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);
        var param = new MapSqlParameterSource()
                .addValue("id", id);
        Member member = namedJdbcTemplate.queryForObject(sql, param, ROW_MAPPER);
        return Optional.ofNullable(member);
    }

    /*
       id가 빈 리스트가 되면 id in () 이런 쿼리가 나간다 -> sql 파싱 실패

     */
    public List<Member> findAllByIdIn(List<Long> ids){
        if (ids.isEmpty()) {
            return List.of();
        }
        var sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE);
        var params = new MapSqlParameterSource().addValue("ids", ids);
        return namedJdbcTemplate.query(sql,params,ROW_MAPPER);
    }
    public Member save(Member member){
        /*
            member id를 보고 갱신 또는 삽입을 정한다
            반환값은 id를 담아서 반환한다
         */
        if(member.getId() == null){
            return insert(member);
        }

        return update(member);
    }

    //JDBC 템플릿 사용
    private Member insert(Member member){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedJdbcTemplate.getJdbcTemplate())
                .withTableName("Member")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        var id =  simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthDay(member.getBirthDay())
                .createdAt(member.getCreatedAt())
                .build();
    }

    private Member update(Member member){
        var sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthDay = :birthDay WHERE id = :id", TABLE);
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        namedJdbcTemplate.update(sql, params);
        return member;
    }
}

package com.example.playgroundmysql.domain.member.repository;

import com.example.playgroundmysql.domain.member.entity.MemberNicknameHistory;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
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
public class MemberNicknameHistoryRepository {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    private static final String TABLE = "MemberNicknameHistory";

    static final RowMapper<MemberNicknameHistory> rowMapper = (ResultSet resultSet, int rowNum) -> MemberNicknameHistory
            .builder()
            .id(resultSet.getLong("id"))
            .memberId(resultSet.getLong("memberId"))
            .nickname(resultSet.getString("nickname"))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    public List<MemberNicknameHistory> findAllByMemberId(Long memberId){
        var sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE);
        var params = new MapSqlParameterSource("memberId", memberId);
        return namedJdbcTemplate.query(sql, params, rowMapper);
    }
    public MemberNicknameHistory save(MemberNicknameHistory history){
        /*
            member id를 보고 갱신 또는 삽입을 정한다
            반환값은 id를 담아서 반환한다
         */
        if(history.getId() == null){
            return insert(history);
        }

        throw new UnsupportedOperationException("MemberNicknameHistory는 갱신을 지원하지 않습니다");
    }

    //JDBC 템플릿 사용
    private MemberNicknameHistory insert(MemberNicknameHistory history){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(history);
        var id =  simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return MemberNicknameHistory.builder()
                .id(id)
                .memberId(history.getMemberId())
                .nickname(history.getNickname())
                .createdAt(history.getCreatedAt())
                .build();
    }
}

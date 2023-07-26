package com.trading212.code212.repositories.mappers;

import com.trading212.code212.repositories.entities.Role;
import com.trading212.code212.repositories.entities.SolutionCodeEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SolutionCodeRowMapper implements RowMapper<SolutionCodeEntity> {

    private final JdbcTemplate jdbcTemplate;

    public SolutionCodeRowMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SolutionCodeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SolutionCodeEntity(
                rs.getLong("code_submitted_id"),
                rs.getString("code_url"),
                rs.getLong("user_id"),
                rs.getLong("problem_id"),
                rs.getLong("language_id"),
                rs.getLong("status_id")
//                getLanguage(rs.getInt("language_id")),
//                getStatus(rs.getInt("status_id"))
        );
    }

//    private String getLanguage(Integer languageId) {
//        String sql = """
//            SELECT l.name
//            FROM language l
//            WHERE l.language_id = ?
//        """;
//        return jdbcTemplate.queryForObject(sql, new Object[]{languageId}, String.class);
//    }
//
//    private String getStatus(Integer statusId) {
//        String sql = """
//            SELECT name
//            FROM status
//            WHERE status_id = ?
//        """;
//        return jdbcTemplate.queryForObject(sql, new Object[]{statusId}, String.class);
//    }
}

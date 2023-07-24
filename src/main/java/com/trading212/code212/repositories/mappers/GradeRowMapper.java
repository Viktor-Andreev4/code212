package com.trading212.code212.repositories.mappers;

import com.trading212.code212.repositories.entities.GradeEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GradeRowMapper implements RowMapper<GradeEntity> {

    private final JdbcTemplate jdbcTemplate;

    public GradeRowMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GradeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new GradeEntity(
                rs.getLong("grade_id"),
                new UserRowMapper(jdbcTemplate).mapRow(rs, rowNum),
                rs.getInt("grade"),
                new ExamRowMapper(jdbcTemplate).mapRow(rs, rowNum)
        );
    }
}

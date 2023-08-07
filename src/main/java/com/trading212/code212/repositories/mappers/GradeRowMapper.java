package com.trading212.code212.repositories.mappers;

import com.trading212.code212.repositories.entities.GradeEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GradeRowMapper implements RowMapper<GradeEntity> {

    @Override
    public GradeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new GradeEntity(
                rs.getLong("grade_id"),
                rs.getLong("user_id"),
                rs.getInt("test_cases_grade"),
                rs.getString("report"),
                rs.getInt("exam_id"),
                rs.getInt("problem_id")
        );
    }
}

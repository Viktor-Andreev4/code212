package com.trading212.code212.repositories.mappers;

import com.trading212.code212.repositories.entities.ExamProblemEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ExamProblemRowMapper implements RowMapper<ExamProblemEntity> {
    @Override
    public ExamProblemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ExamProblemEntity(
                rs.getLong("exam_id"),
                rs.getLong("problem_id")
        );
    }
}

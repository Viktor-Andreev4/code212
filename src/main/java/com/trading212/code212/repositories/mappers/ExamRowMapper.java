package com.trading212.code212.repositories.mappers;

import com.trading212.code212.repositories.entities.ExamEntity;
import com.trading212.code212.repositories.entities.ProblemEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class ExamRowMapper implements RowMapper<ExamEntity> {

    private final JdbcTemplate jdbcTemplate;
    private final ProblemRowMapper problemRowMapper;

    public ExamRowMapper(JdbcTemplate jdbcTemplate, ProblemRowMapper problemRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.problemRowMapper = problemRowMapper;
    }

    @Override
    public ExamEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ExamEntity(
                rs.getLong("exam_id"),
                rs.getString("name"),
                rs.getTimestamp("start_date").toLocalDateTime(),
                rs.getTimestamp("end_date").toLocalDateTime(),
                getProblemsByExamId(rs.getLong("exam_id"))
        );
    }

    private Set<ProblemEntity> getProblemsByExamId(Long problemId) {
        var sql = """
                SELECT p.*
                FROM problem p
                INNER JOIN exam_problem ep ON p.problem_id = ep.problem_id
                INNER JOIN exam e ON ep.exam_id = e.exam_id
                WHERE ep.exam_id = ?
                """;
        return new HashSet<>(
                jdbcTemplate.query(
                sql,
                problemRowMapper,
                problemId
        ));
    }
}

package com.trading212.code212.repositories.mysql;

import com.trading212.code212.repositories.GradeRepository;
import com.trading212.code212.repositories.entities.GradeEntity;
import com.trading212.code212.repositories.mappers.GradeRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MySQLGradeRepository implements GradeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate txTemplate;
    private final GradeRowMapper gradeRowMapper;

    public MySQLGradeRepository(JdbcTemplate jdbcTemplate, TransactionTemplate txTemplate, GradeRowMapper gradeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.txTemplate = txTemplate;
        this.gradeRowMapper = gradeRowMapper;
    }

    @Override
    public GradeEntity createGrade(Long userId, Integer grade, Long problemId) {
        var sql = """
                INSERT INTO grades (user_id, grade, problem_id)
                VALUES (?, ?, ?)
                """;
        return txTemplate.execute(status -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                var ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, userId);
                ps.setInt(2, grade);
                ps.setLong(3, problemId);
                return ps;
            }, keyHolder);
            Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            return new GradeEntity(id, userId, grade, problemId);
        });
    }

    @Override
    public Optional<GradeEntity> getGradeProblem(Long userId, Long problemId) {
        var sql = """
                SELECT *
                FROM grades
                WHERE user_id = ? AND problem_id = ?
                """;
        return jdbcTemplate.query(sql, gradeRowMapper, userId, problemId)
                .stream()
                .findFirst();
    }
}

package com.trading212.code212.repositories.mysql;

import com.trading212.code212.core.models.GradeDTO;
import com.trading212.code212.repositories.GradeRepository;
import com.trading212.code212.repositories.entities.GradeEntity;
import com.trading212.code212.repositories.mappers.GradeRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Statement;
import java.util.List;
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
    public GradeEntity createGrade(Long userId, Integer grade, String report,int examId, int problemId) {
        var sql = """
                INSERT INTO grade (user_id, test_cases_grade, report, exam_id, problem_id)
                VALUES (?, ?, ?, ?, ?)
                """;
        return txTemplate.execute(status -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                var ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, userId);
                ps.setInt(2, grade);
                ps.setString(3, report);
                ps.setInt(4, examId);
                ps.setInt(5, problemId);
                return ps;
            }, keyHolder);
            Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            return new GradeEntity(id, userId, grade, report, examId, problemId);
        });
    }

    @Override
    public List<GradeEntity> getStudentGradeForExamProblem(int examId, Long userId) {
        var sql = """
                SELECT g.grade_id, g.user_id, g.test_cases_grade, g.report, g.exam_id, g.problem_id
                FROM grade g
                JOIN problem p ON g.problem_id = p.problem_id
                JOIN exam e ON g.exam_id = e.exam_id
                WHERE g.user_id = ? AND e.exam_id = ?
                """;
        return jdbcTemplate.query(sql, gradeRowMapper, userId, examId);
    }

    @Override
    public void editReport(int gradeId, String report) {
        var sql = """
                UPDATE grade
                SET report = ?
                WHERE grade_id = ?
                """;
        txTemplate.execute(status -> {
            return jdbcTemplate.update(sql, report, gradeId);
        });
    }

    @Override
    public GradeEntity getGradeById(int gradeId) {
        var sql = """
                SELECT grade_id, user_id, test_cases_grade, report, exam_id, problem_id
                FROM grade 
                WHERE grade_id = ?
                """;
        return jdbcTemplate.queryForObject(sql, gradeRowMapper, gradeId);
    }


}

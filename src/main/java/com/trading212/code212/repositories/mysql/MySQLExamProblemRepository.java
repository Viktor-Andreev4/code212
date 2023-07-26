package com.trading212.code212.repositories.mysql;

import com.trading212.code212.repositories.ExamProblemRepository;
import com.trading212.code212.repositories.entities.ExamEntity;
import com.trading212.code212.repositories.entities.ExamProblemEntity;
import com.trading212.code212.repositories.mappers.ExamProblemRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class MySQLExamProblemRepository implements ExamProblemRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate txTemplate;
    private final ExamProblemRowMapper examProblemRowMapper;

    public MySQLExamProblemRepository(JdbcTemplate jdbcTemplate, TransactionTemplate txTemplate, ExamProblemRowMapper examProblemRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.txTemplate = txTemplate;
        this.examProblemRowMapper = examProblemRowMapper;
    }

    @Override
    public ExamProblemEntity addProblemToExam(ExamProblemEntity examProblemEntity) {
        var sql = """
                INSERT INTO exam_problem (exam_id, problem_id)
                VALUES (?, ?)
                """;
        return txTemplate.execute(status -> {
            jdbcTemplate.update(sql, examProblemEntity.examId(), examProblemEntity.problemId());
            return examProblemEntity;
        });
    }

    @Override
    public boolean updateExamProblem(ExamProblemEntity examProblemEntity, Long newProblemId) {
        var sql = """
                UPDATE exam_problem
                SET problem_id = ?
                WHERE exam_id = ? AND problem_id = ?
                """;
        return jdbcTemplate.update(sql, newProblemId, examProblemEntity.examId(), examProblemEntity.problemId()) > 0;
    }

    @Override
    public Optional<ExamProblemEntity> getExamProblem(Long examId, Long problemId) {
        var sql = """
                SELECT exam_id, problem_id
                FROM exam_problem
                WHERE exam_id = ? AND problem_id = ?
                """;
        return jdbcTemplate.query(sql, examProblemRowMapper, examId, problemId)
                .stream()
                .findFirst();
    }

    @Override
    public void deleteExamProblem(Long examId, Long problemId) {
        var sql = """
                DELETE FROM exam_problem
                WHERE exam_id = ? AND problem_id = ?
                """;
        txTemplate.execute(status ->  {
            jdbcTemplate.update(sql, examId, problemId);
            return null;
        });

    }
}

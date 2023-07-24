package com.trading212.code212.repositories.mysql;

import com.trading212.code212.repositories.ExamRepository;
import com.trading212.code212.repositories.entities.ExamEntity;
import com.trading212.code212.repositories.entities.ProblemEntity;
import com.trading212.code212.repositories.mappers.ExamRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MySQLExamRepository implements ExamRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate txTemplate;
    private final ExamRowMapper examRowMapper;

    public MySQLExamRepository(JdbcTemplate jdbcTemplate, TransactionTemplate txTemplate, ExamRowMapper examRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.txTemplate = txTemplate;
        this.examRowMapper = examRowMapper;
    }

    @Override
    public ExamEntity createExam(String name, LocalDateTime startDate, LocalDateTime endDate) {
        var sql = """
                INSERT INTO exam (name, start_date, end_date)
                VALUES (?, ?, ?)
                """;
        return txTemplate.execute(status -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, name);
                ps.setTimestamp(2, Timestamp.valueOf(startDate));
                ps.setTimestamp(3, Timestamp.valueOf(endDate));
                return ps;
            }, keyHolder);
            var id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            return new ExamEntity(id, name, startDate, endDate);
        });
    }

    @Override
    public Optional<ExamEntity> getExamById(int id) {
        var sql = """
                SELECT *
                FROM exam
                WHERE exam_id = ?
                """;
        return jdbcTemplate.query(sql, examRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void deleteExamById(int id) {
        var sql = """
                DELETE FROM exam
                WHERE exam_id = ?
                """;
        txTemplate.execute(status ->  {
           jdbcTemplate.update(sql, id);
              return null;
        });
    }

    @Override
    public boolean addProblemToExam(int examID, int problemID) {
        var sql = """
                INSERT INTO exam_problem (exam_id, problem_id)
                VALUES (?, ?)
                """;
        return jdbcTemplate.update(sql, examID, problemID) > 0;
    }
}

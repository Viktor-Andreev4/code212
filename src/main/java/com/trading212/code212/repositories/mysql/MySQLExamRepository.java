package com.trading212.code212.repositories.mysql;

import com.trading212.code212.core.models.ExamDTO;
import com.trading212.code212.repositories.ExamRepository;
import com.trading212.code212.repositories.entities.ExamEntity;
import com.trading212.code212.repositories.entities.ProblemEntity;
import com.trading212.code212.repositories.mappers.ExamRowMapper;
import com.trading212.code212.repositories.mappers.ProblemRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class MySQLExamRepository implements ExamRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate txTemplate;
    private final ExamRowMapper examRowMapper;
    private final ProblemRowMapper problemRowMapper;

    public MySQLExamRepository(JdbcTemplate jdbcTemplate, TransactionTemplate txTemplate, ExamRowMapper examRowMapper, ProblemRowMapper problemRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.txTemplate = txTemplate;
        this.examRowMapper = examRowMapper;
        this.problemRowMapper = problemRowMapper;
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
                SELECT exam_id, name, start_date, end_date
                FROM exam
                WHERE exam_id = ?
                """;

        Optional<ExamEntity> first = jdbcTemplate.query(sql, examRowMapper, id)
                .stream()
                .findFirst();
        System.out.println(first);
        return first;
    }

    @Override
    public void deleteExamById(Long id) {
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
    public boolean addProblemToExam(Long examID, Long problemID) {
        var sql = """
                INSERT INTO exam_problem (exam_id, problem_id)
                VALUES (?, ?)
                """;
        return jdbcTemplate.update(sql, examID, problemID) > 0;
    }

    @Override
    public Set<ProblemEntity> getProblemsForExam(Long examID) {
        var sql = """
        SELECT p.problem_id, p.title, p.description, p.input_url, p.output_url
        FROM problem p
        INNER JOIN exam_problem ep ON p.problem_id = ep.problem_id
        WHERE ep.exam_id = ?
        """;

        return new HashSet<>(
                jdbcTemplate.query(sql, problemRowMapper, examID)
        );
    }

    @Override
    public ExamEntity getUpcomingOrOngoingExam(LocalDateTime currentTime) {
        var sql = """
                SELECT exam_id, name, start_date, end_date
                FROM exam
                WHERE start_date <= ? AND end_date >= ?
                UNION
                SELECT exam_id, name, start_date, end_date
                FROM exam
                WHERE start_date > ?
                ORDER BY start_date ASC
                LIMIT 1
                """;
        List<ExamEntity> exams =
                jdbcTemplate.query(sql, examRowMapper, currentTime, currentTime, currentTime);
        return exams.isEmpty() ? null : exams.get(0);
    }

    @Override
    public List<ExamEntity> getAllExams() {
        var sql = """
                SELECT exam_id, name, start_date, end_date
                FROM exam
                """;
        List<ExamEntity> query = jdbcTemplate.query(sql, examRowMapper);
        return query;
    }


}

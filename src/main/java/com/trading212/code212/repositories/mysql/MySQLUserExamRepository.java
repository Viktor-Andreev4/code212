package com.trading212.code212.repositories.mysql;

import com.trading212.code212.repositories.UserExamRepository;
import com.trading212.code212.repositories.entities.UserEntity;
import com.trading212.code212.repositories.mappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Repository
public class MySQLUserExamRepository implements UserExamRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate txTemplate;
    private final UserRowMapper userRowMapper;

    public MySQLUserExamRepository(JdbcTemplate jdbcTemplate, TransactionTemplate txTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.txTemplate = txTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public void insertUserExam(Long userId, Long examId) {
        var sql = """
                INSERT INTO exam_user (exam_id, user_id)
                VALUES (?, ?)
                """;
        txTemplate.execute(status -> {
            jdbcTemplate.update(sql, userId, examId);
            return null;
        });
    }

    @Override
    public List<UserEntity> getAllUsersForExamWithId(Long examId) {
        var sql = """
                SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, u.role_id
                FROM user u
                JOIN exam_user ue ON u.user_id = ue.user_id
                WHERE exam_id = ?
                """;
        return jdbcTemplate.query(sql, userRowMapper, examId);
    }

    @Override
    public boolean userIsEnrolled(Long examId, Long userId) {
        var sql = """
                SELECT COUNT(*) > 0
                FROM exam_user
                WHERE exam_id = ? AND user_id = ? 
                """;
        return jdbcTemplate.queryForObject(sql, Boolean.class, examId, userId);
    }
}

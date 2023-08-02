package com.trading212.code212.repositories.mysql;

import com.trading212.code212.repositories.CodeRepository;
import com.trading212.code212.repositories.entities.SolutionCodeEntity;
import com.trading212.code212.repositories.mappers.SolutionCodeRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Statement;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Repository
public class MySQLCodeRepository implements CodeRepository {


    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate txTemplate;
    private final SolutionCodeRowMapper solutionCodeRowMapper;

    public MySQLCodeRepository(
            JdbcTemplate jdbcTemplate,
            TransactionTemplate txTemplate,
            SolutionCodeRowMapper solutionCodeRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.txTemplate = txTemplate;
        this.solutionCodeRowMapper = solutionCodeRowMapper;
    }

    @Override
    public SolutionCodeEntity insertSolutionCode(String codeUrl, long userId, long problemId, long languageId, long statusId) {
        var sql = """
                INSERT INTO solution_code (code_url, user_id, problem_id, language_id, status_id)
                     VALUES (?, ?, ?, ?, ?)
                """;
        return txTemplate.execute(status -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, codeUrl);
                ps.setLong(2, userId);
                ps.setLong(3, problemId);
                ps.setLong(4, languageId);
                ps.setLong(5, statusId);
                return ps;
            }, keyHolder);
            Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            return new SolutionCodeEntity(id, codeUrl, userId, problemId, languageId, statusId);
        });
    }

    @Override
    public Set<SolutionCodeEntity> getSolutionCodeProblemByUserId(long userId, long problemId) {
        var sql = """
                SELECT (code_submitted_id, code_url, user_id, problem_id, language_id, status_id)
                FROM solution_code
                WHERE user_id = ? AND problem_id = ?
                """;
        return new HashSet<>(Objects.requireNonNull(jdbcTemplate.query(sql, solutionCodeRowMapper, userId, problemId)));
    }
}

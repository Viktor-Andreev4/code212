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
import java.util.Optional;
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
    public SolutionCodeEntity insertSolutionCode(long userId, int problemId, int languageId, int statusId) {
        var sql = """
                INSERT INTO solution_code (code_url, user_id, problem_id, language_id, status_id)
                     VALUES (?, ?, ?, ?, ?)
                """;
        return txTemplate.execute(status -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, userId);
                ps.setLong(2, problemId);
                ps.setLong(3, languageId);
                ps.setLong(4, statusId);
                return ps;
            }, keyHolder);
            Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            return getSolutionCodeById(id).get();
        });
    }


    @Override
    public Optional<SolutionCodeEntity> getSolutionCodeById(long id) {
        var sql = """
                SELECT code_submitted_id, user_id, problem_id, language_id, status_id
                FROM solution_code
                WHERE code_submitted_id = ?
                """;
        return jdbcTemplate.query(sql, new Object[]{id}, solutionCodeRowMapper).stream().findFirst();
    }
}

package com.trading212.code212.repositories.mysql;

import com.trading212.code212.repositories.ProblemRepository;
import com.trading212.code212.repositories.entities.ProblemEntity;
import com.trading212.code212.repositories.mappers.ProblemRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MySQLProblemRepository implements ProblemRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate txTemplate;
    private final ProblemRowMapper problemRowMapper;

    public MySQLProblemRepository(JdbcTemplate jdbcTemplate, TransactionTemplate txTemplate, ProblemRowMapper problemRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.txTemplate = txTemplate;
        this.problemRowMapper = problemRowMapper;
    }


    @Override
    public ProblemEntity createProblem(String title, String description, String inputUrl, String outputUrl) {
        var sql = """
                INSERT INTO problem (title, description, input_url, output_url)
                VALUES (?, ?, ?, ?)
                """;
        return txTemplate.execute(status -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                var ps = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, title);
                ps.setString(2, description);
                ps.setString(3, inputUrl);
                ps.setString(4, outputUrl);
                return ps;
            }, keyHolder);
            Integer id = Objects.requireNonNull(keyHolder.getKey()).intValue();
            return new ProblemEntity(
                    id,
                    title,
                    description,
                    inputUrl,
                    outputUrl
            );
        });
    }

    @Override
    public Optional<ProblemEntity> getProblemById(int id) {
        var sql = """
                SELECT *
                FROM problem
                WHERE problem_id = ?
                """;
        return jdbcTemplate.query(sql, problemRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void deleteProblemById(int id) {
        var sql = """
                DELETE FROM problem
                WHERE problem_id = ?
                """;
        txTemplate.execute(status -> {
            jdbcTemplate.update(sql, id);
            return null;
        });
    }
}

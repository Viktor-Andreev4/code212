package com.trading212.code212.repositories.mysql;

import com.trading212.code212.repositories.StatusRepository;
import com.trading212.code212.repositories.entities.StatusEntity;
import com.trading212.code212.repositories.mappers.StatusRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;

@Repository
public class MySQLStatusRepository implements StatusRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;
    private final StatusRowMapper statusRowMapper;

    public MySQLStatusRepository(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate, StatusRowMapper statusRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
        this.statusRowMapper = statusRowMapper;
    }

    @Override
    public StatusEntity addStatus(int id, String name) {
        var sql = """
                INSERT INTO status (status_id, name)
                VALUES (?, ?)
                """;
        return transactionTemplate.execute(statusTransactionStatus -> {
            jdbcTemplate.update(sql, id, name);
            return new StatusEntity(id, name);
        });
    }

    @Override
    public Optional<StatusEntity> getStatusById(int id) {
        var sql = """
                SELECT status_id, name
                FROM status
                WHERE status_id = ?
                """;
        return jdbcTemplate.query(sql, statusRowMapper, id).stream().findFirst();
    }
}

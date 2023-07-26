package com.trading212.code212.repositories.mysql;

import com.trading212.code212.repositories.UserRepository;
import com.trading212.code212.repositories.entities.UserEntity;
import com.trading212.code212.repositories.mappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MySQLUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate txTemplate;
    private UserRowMapper userRowMapper;

    public MySQLUserRepository(JdbcTemplate jdbcTemplate, TransactionTemplate txTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.txTemplate = txTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public Optional<UserEntity> getUserById(Integer id) {
        var sql = """
                SELECT user_id, first_name, last_name, email, password, role_id
                FROM user
                WHERE user_id = ?
                """;
        return jdbcTemplate.query(sql, userRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public List<UserEntity> getAllUsers() {
        var sql = """
                SELECT user_id, first_name, last_name, email, password, role_id
                FROM user
                LIMIT 100
                """;

        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public UserEntity insertUser(UserEntity user) {
        var sql = """
                INSERT INTO user (first_name, last_name, email, password)
                     VALUES (?, ?, ?, ?)
                """;
        return txTemplate.execute(status -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPassword());
                return ps;
            }, keyHolder);
            Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            return new UserEntity(
                    id,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRoles());
        });
    }

    @Override
    public boolean existsUserWithEmail(String email) {
        String sql = """
                    SELECT COUNT(*)
                            FROM user
                            WHERE email = ?
                    """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsUserById(Integer userId) {
        var sql = """
                SELECT COUNT(*) 
                           FROM user
                          WHERE user_id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class, userId);
        return count != null && count > 0;
    }

    @Override
    public void deleteUserById(Integer userId) {
        var sql = """
                DELETE FROM user
                      WHERE user_id = ?
                """;
        txTemplate.execute(status -> {
            jdbcTemplate.update(sql, userId);
            return null;
        });
    }

    @Override
    public Optional<UserEntity> selectUserByEmail(String email) {
        var sql = """
                SELECT user_id, first_name, last_name, email, password, role_id
                FROM user
                WHERE email = ?
                """;
        return jdbcTemplate.query(sql, userRowMapper, email)
                .stream()
                .findFirst();
    }
}

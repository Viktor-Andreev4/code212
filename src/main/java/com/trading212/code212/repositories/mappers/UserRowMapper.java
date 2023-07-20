package com.trading212.code212.repositories.mappers;

import com.trading212.code212.repositories.entities.Role;
import com.trading212.code212.repositories.entities.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserRowMapper implements RowMapper<UserEntity> {
    private final JdbcTemplate jdbcTemplate;

    public UserRowMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserEntity(
                rs.getInt("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password"),
                getUserRoles(rs.getInt("user_id"))
        );
    }

    private Set<Role> getUserRoles(Integer userId) {
        var sql = """ 
                SELECT r.role_id, r.name
                FROM role r
                INNER JOIN user_role ur ON r.role_id = ur.role_id
                WHERE ur.user_id = ?
                """;
        List<Role> list = jdbcTemplate.queryForList(sql, new Object[]{userId}, Role.class);
        return new HashSet<>(list);
    }
}

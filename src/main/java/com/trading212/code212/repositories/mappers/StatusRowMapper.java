package com.trading212.code212.repositories.mappers;

import com.trading212.code212.repositories.entities.StatusEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StatusRowMapper implements RowMapper<StatusEntity> {
    @Override
    public StatusEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StatusEntity(
                rs.getInt("status_id"),
                rs.getString("name")
        );
    }
}

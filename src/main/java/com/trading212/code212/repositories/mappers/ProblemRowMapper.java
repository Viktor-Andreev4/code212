package com.trading212.code212.repositories.mappers;

import com.trading212.code212.repositories.entities.ProblemEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProblemRowMapper implements RowMapper<ProblemEntity> {
    @Override
    public ProblemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProblemEntity(
                rs.getInt("problem_id"),
                rs.getString("title"),
                rs.getString("description")
        );
    }
}

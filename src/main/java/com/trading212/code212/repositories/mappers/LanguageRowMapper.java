package com.trading212.code212.repositories.mappers;

import com.trading212.code212.repositories.entities.LanguageEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LanguageRowMapper implements RowMapper<LanguageEntity> {
    @Override
    public LanguageEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new LanguageEntity(
                rs.getInt("language_id"),
                rs.getString("name")
        );
    }
}

package com.trading212.code212.repositories.mysql;

import com.trading212.code212.repositories.LanguageRepository;
import com.trading212.code212.repositories.entities.LanguageEntity;
import com.trading212.code212.repositories.mappers.LanguageRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MySQLLanguageRepository implements LanguageRepository {
    private final JdbcTemplate jdbcTemplate;
    private final LanguageRowMapper languageRowMapper;

    public MySQLLanguageRepository(JdbcTemplate jdbcTemplate, LanguageRowMapper languageRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.languageRowMapper = languageRowMapper;
    }

    @Override
    public LanguageEntity getLanguageByName(String language) {
        var sql = "SELECT id FROM languages WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, languageRowMapper, language);
    }
}

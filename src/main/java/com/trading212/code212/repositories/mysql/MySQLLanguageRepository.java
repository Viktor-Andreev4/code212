package com.trading212.code212.repositories.mysql;

import com.trading212.code212.repositories.LanguageRepository;
import com.trading212.code212.repositories.entities.LanguageEntity;
import com.trading212.code212.repositories.mappers.LanguageRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MySQLLanguageRepository implements LanguageRepository {
    private final JdbcTemplate jdbcTemplate;
    private final LanguageRowMapper languageRowMapper;

    public MySQLLanguageRepository(JdbcTemplate jdbcTemplate, LanguageRowMapper languageRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.languageRowMapper = languageRowMapper;
    }

    @Override
    public Optional<LanguageEntity> getLanguageByName(String language) {
        System.out.println(language);
        var sql = "SELECT language_id, name FROM language WHERE name = ?";
        return jdbcTemplate.query(sql, languageRowMapper, language).stream().findFirst();
    }

    @Override
    public Optional<LanguageEntity> getLanguageById(Integer languageId) {
        String sql = """
            SELECT language_id, name
            FROM language 
            WHERE language_id = ?
        """;
        return jdbcTemplate.query(sql, languageRowMapper, LanguageEntity.class).stream().findFirst();
    }
}

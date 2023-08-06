package com.trading212.code212.repositories.mappers;

import com.trading212.code212.repositories.ExamRepository;
import com.trading212.code212.repositories.ProblemRepository;
import com.trading212.code212.repositories.UserRepository;
import com.trading212.code212.repositories.entities.LanguageEntity;
import com.trading212.code212.repositories.entities.SolutionCodeEntity;
import com.trading212.code212.repositories.entities.StatusEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SolutionCodeRowMapper implements RowMapper<SolutionCodeEntity> {

    private final JdbcTemplate jdbcTemplate;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final LanguageRowMapper languageRowMapper;
    private final StatusRowMapper statusRowMapper;
    private final ExamRepository examRepository;

    public SolutionCodeRowMapper(JdbcTemplate jdbcTemplate, ProblemRepository problemRepository, UserRepository userRepository, LanguageRowMapper languageRowMapper, StatusRowMapper statusRowMapper, ExamRepository examRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
        this.languageRowMapper = languageRowMapper;
        this.statusRowMapper = statusRowMapper;
        this.examRepository = examRepository;
    }

    @Override
    public SolutionCodeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        System.out.println("SolutionCodeRowMapper.mapRow");
        System.out.println("user repository" + userRepository.getUserById(rs.getLong("user_id")).get());
        System.out.println("exam repository" + examRepository.getExamById(rs.getInt("exam_id")).get());
        System.out.println("problem repository" + problemRepository.getProblemById((rs.getInt("problem_id"))).get());
        return new SolutionCodeEntity(
                rs.getLong("code_submitted_id"),
                userRepository.getUserById(rs.getLong("user_id")).get(),
                examRepository.getExamById(rs.getInt("exam_id")).get(),
                problemRepository.getProblemById((rs.getInt("problem_id"))).get(),
                getLanguage(rs.getInt("language_id")),
                getStatus(rs.getInt("status_id"))
        );
    }



    private LanguageEntity getLanguage(Integer languageId) {
        String sql = """
            SELECT language_id, name
            FROM language 
            WHERE language_id = ?
        """;
        return jdbcTemplate.queryForObject(sql, languageRowMapper, languageId);
    }

    private StatusEntity getStatus(Integer statusId) {
        String sql = """
            SELECT status_id, name
            FROM status
            WHERE status_id = ?
        """;
        return jdbcTemplate.queryForObject(sql, statusRowMapper, statusId);
    }
}

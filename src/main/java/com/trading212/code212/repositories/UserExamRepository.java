package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.UserEntity;
import org.apache.catalina.User;

import java.util.List;

public interface UserExamRepository {

    void insertUserExam(Long userId, Long examId);

    List<UserEntity> getAllUsersForExamWithId(Long examId);

    boolean userIsEnrolled(Long examId, Long userId);
}

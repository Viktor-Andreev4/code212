package com.trading212.code212.core;

import com.trading212.code212.core.models.SolutionCodeDTO;
import com.trading212.code212.exceptions.FileFailedUploadException;
import com.trading212.code212.repositories.UserRepository;
import com.trading212.code212.s3.S3Buckets;
import com.trading212.code212.s3.S3Service;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CodeService {

    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

    public CodeService(UserRepository userRepository, S3Service s3Service, S3Buckets s3Buckets) {
        this.userRepository = userRepository;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
    }

    public SolutionCodeDTO insertSolutionCode() {
        return null;
    }

    public byte[] getUserCode(Long userId) {
        return null;
    }

    public void uploadCustomerProfileImage(Long userId, Long problemId, MultipartFile file) {
        if (!userRepository.existsUserWithId(userId)) {
            throw new IllegalArgumentException("User with id " + userId + " does not exist");
        }

        try {
            s3Service.putObject(
                    s3Buckets.getUser(),
                    "code/%s/%s".formatted(userId, problemId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new FileFailedUploadException(
                    "Failed to upload file problem with id %s by %s user", problemId, userId);
        }
    }
}

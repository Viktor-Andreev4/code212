package com.trading212.code212.core;

import com.trading212.code212.core.models.SolutionCodeDTO;
import com.trading212.code212.s3.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CodeService {

    private final S3Service s3Service;

    public CodeService(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    public SolutionCodeDTO insertSolutionCode() {
        return null;
    }

    public byte[] getUserCode(Long userId) {
        return null;
    }

    public void uploadCustomerProfileImage(Long userId, MultipartFile file) {
    }
}

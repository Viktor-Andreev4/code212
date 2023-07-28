package com.trading212.code212.exceptions;

public class FileFailedUploadException extends RuntimeException{

    public FileFailedUploadException(String message, Long problemId, Long userId) {
        super(message.formatted(problemId, userId));
    }
}

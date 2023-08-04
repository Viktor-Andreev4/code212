package com.trading212.code212.s3;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final AmazonS3 amazonS3Client;

    public S3Service(S3Client s3Client, AmazonS3 amazonS3Client) {
        this.s3Client = s3Client;
        this.amazonS3Client = amazonS3Client;
    }

    public void putObject(String bucketName, String key, byte[] file) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file));
    }

    public byte[] getObject(String bucketName, String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);
        try {
            return response.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<byte[]> getObjects(String bucketName, String folderKey) {

        ListObjectsV2Request listObjectsReqManual = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(folderKey)
                .build();

        ListObjectsV2Response listObjectsRes = s3Client.listObjectsV2(listObjectsReqManual);

        List<byte[]> fileDataList = new ArrayList<>();

        for (S3Object s3Object : listObjectsRes.contents()) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Object.key())
                    .build();

            try {
                ResponseInputStream<GetObjectResponse> objectData = s3Client.getObject(getObjectRequest);
                fileDataList.add(objectData.readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return fileDataList;
    }
}

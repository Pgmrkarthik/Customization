package com.dev.customization.service.AWS;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class S3Service{



    private final S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    Logger LOG = LogManager.getLogger(S3Service.class);


    public S3Service(@Value("${aws.accessKeyId}") String accessKeyId,
                     @Value("${aws.secretAccessKey}") String secretAccessKey
                    ) {
        AwsBasicCredentials awsCredos = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        this.s3Client = S3Client.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredos))
                .build();
    }

    public String uploadFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(path.getFileName().toString())
                    .build();
            s3Client.putObject(putObjectRequest, path);
            return "File uploaded: " + path.getFileName().toString();
        } catch (S3Exception e) {
            throw new RuntimeException("Error uploading file to S3: " + e.getMessage(), e);
        }
    }









}

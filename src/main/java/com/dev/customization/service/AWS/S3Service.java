package com.dev.customization.service.AWS;


import com.dev.customization.configuration.UserInfoConfig;
import com.dev.customization.entity.UserCredentials;
import com.dev.customization.security.JWTUtil;
import com.dev.customization.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public String uploadFile(String filePath, String fieldName) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println("User email: " + email);

        String[] parts = fieldName.split("(?<=\\D)(?=\\d)"); // Split into letters and numbers
        String fileType = parts.length > 0 ? parts[0] : ""; // e.g., "video", "audio", "image"
        String position = parts.length > 1 ? parts[1] : "";

        String folderPath = fileType + "/";

        String newFileName = email+fieldName;
        try {
            Path path = Paths.get(filePath);
            String originalFileName = path.getFileName().toString();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(folderPath+newFileName+fileExtension)
                    .build();
            s3Client.putObject(putObjectRequest, path);
            return "File uploaded: " + path.getFileName().toString();
        } catch (S3Exception e) {
            throw new RuntimeException("Error uploading file to S3: " + e.getMessage(), e);
        }
    }









}

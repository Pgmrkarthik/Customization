package com.dev.customization.service.AWS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


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

    public String newUploadFile(MultipartFile file) throws IOException {

        Path tempFile = Files.createTempFile(String.valueOf(System.currentTimeMillis()),"_"+ file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

        String filePath = tempFile.toString();

        String fileName = tempFile.getFileName().toString();

        try {
            Path path = Paths.get(filePath);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
            s3Client.putObject(putObjectRequest, path);
            return fileName;
        } catch (S3Exception e) {
            throw new RuntimeException("Error uploading file to S3: " + e.getMessage(), e);
        }
    }
    public String copyFolder(Long randomId) {

        String sourceFolderPath = "VirtualSpaces/Templates";

//        System.out.println(sourceFolderPath);
        System.out.println(randomId);
        // 1. List all objects in the source folder
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(sourceFolderPath +"/") // Ensure it lists objects within the folder
                .build();

        ListObjectsV2Response listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);

        System.out.println(listObjectsResponse);

        // Copy each object from source to target with the new randomId
        for (S3Object s3Object : listObjectsResponse.contents()) {
            String sourceKey = s3Object.key(); // e.g., "Visualsbucket/visula/template/file.txt"
            String targetKey = sourceKey.replaceFirst(sourceFolderPath, "VirtualSpaces/" + randomId);


            System.out.println(s3Object);

            System.out.println(sourceKey+" "+targetKey);

            // Create the copy request
            CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                    .sourceBucket(bucketName)
                    .sourceKey(sourceKey)
                    .destinationBucket(bucketName)
                    .destinationKey(targetKey)
                    .build();

            // Execute the copy
            s3Client.copyObject(copyObjectRequest);


        }

        return "http://virtualspacecustomization.s3.ap-south-1.amazonaws.com/VirtualSpaces/"+randomId +"/index.html";
    }

}

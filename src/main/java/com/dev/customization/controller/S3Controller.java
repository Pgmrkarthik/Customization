package com.dev.customization.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import com.dev.customization.service.AWS.S3Service;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/api/s3")
public class S3Controller {


        @Autowired
        public final S3Service s3Service;

        public S3Controller(S3Service s3Service) {
            this.s3Service = s3Service;
        }



//        @GetMapping
//        public void getBucketList() {
//            List<Bucket> bucketList = bucketService.getBucketList();
//            System.out.println("bucketList:"+bucketList);
//        }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile("s3-upload-", file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        String result = s3Service.uploadFile(tempFile.toString());
        Files.delete(tempFile);  // Cleanup temp file
        return result;
    }

}

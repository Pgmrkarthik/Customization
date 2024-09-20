package com.dev.customization.controller;


import com.dev.customization.Enum.MediaType;
import com.dev.customization.entity.virtualspace.spaces.CustomSpace;
import com.dev.customization.entity.virtualspace.spaces.PublishedSpace;
import com.dev.customization.payload.CustomSpaceDTO;
import com.dev.customization.payload.PublishSpaceRequestDTO;
import com.dev.customization.payload.TemplateIdDTO;
import com.dev.customization.service.VirtualSpace.CustomSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/custom_space")
public class CustomSpaceController {
    @Autowired
    private CustomSpaceService customSpaceService;

    @PostMapping("/create")
    public ResponseEntity<CustomSpaceDTO> createCustomSpaceFromTemplate(@RequestBody TemplateIdDTO templateId) throws Exception {

        System.out.println(
                templateId
        );

        CustomSpaceDTO customSpace = customSpaceService.createCustomSpaceFromTemplate(templateId.getTemplateId());
        return new ResponseEntity<>(customSpace, HttpStatus.CREATED);
    }
    @PostMapping("/upload")
    public ResponseEntity<CustomSpaceDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("customSpaceId") Long customSpaceId,
            @RequestParam("position") int position,
            @RequestParam("mediaType") MediaType mediaType) throws IOException {

        System.out.println(position);

        // Call the service method to handle the file upload
        CustomSpaceDTO customSpace = customSpaceService.uploadFile(file, customSpaceId, position, mediaType);

        // Return the response entity with the updated CustomSpace and status
        return new ResponseEntity<>(customSpace, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomSpace>> getUserCustomSpaces(
           ) throws IOException {

        // Call the service method to handle the file upload
       List<CustomSpace>  customSpace = customSpaceService.getAllCustomSpaceByUser();

        // Return the response entity with the updated CustomSpace and status
        return new ResponseEntity<>(customSpace, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<CustomSpaceDTO> getUserCustomSpace(@RequestParam Long spaceId) throws IOException {

        // Call the service method to handle the file upload
        CustomSpaceDTO customSpace = customSpaceService.getCustomSpaceWithMediaFiles(spaceId);

        // Return the response entity with the updated CustomSpace and status
        return new ResponseEntity<>(customSpace, HttpStatus.OK);
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishCustomSpace(@RequestBody PublishSpaceRequestDTO publishSpaceRequestDTO) throws IOException {

        // Call the service method to handle the file upload
        String publishedSpaceUrl = customSpaceService.publishCustomSpace(publishSpaceRequestDTO.getSpaceId(),publishSpaceRequestDTO.getUrl());

        // Return the response entity with the updated CustomSpace and status
        return new ResponseEntity<>(publishedSpaceUrl, HttpStatus.OK);
    }







}

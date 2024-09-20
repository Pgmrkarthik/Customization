package com.dev.customization.controller;


import com.dev.customization.entity.virtualspace.spaces.TemplateSpace;
import com.dev.customization.payload.CustomSpaceDTO;
import com.dev.customization.payload.TemplateSpaceDTO;
import com.dev.customization.service.VirtualSpace.TemplateSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/template")
public class TemplateSpaceController {

    @Autowired
    private TemplateSpaceService templateSpaceService;


    @PostMapping("/create")
    public ResponseEntity<TemplateSpace> createTemplateSpace(@RequestBody TemplateSpace templateSpace) {
        TemplateSpace createdTemplateSpace = templateSpaceService.createTemplateSpace(templateSpace);
        return new ResponseEntity<>(createdTemplateSpace, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TemplateSpace>> getAllTemplates() throws RuntimeException {

            List<TemplateSpace> templateSpaces = templateSpaceService.getAllTemplates();
            if (templateSpaces == null) {
                throw new RuntimeException();
            }

            return ResponseEntity.ok(templateSpaces);

    }
    @GetMapping("/{id}")
    public ResponseEntity<TemplateSpaceDTO> getTemplate(@PathVariable Long id) throws RuntimeException {

        TemplateSpaceDTO templateSpaces = templateSpaceService.getTemplateSpaceWithMedia(id);
        if (templateSpaces == null) {
            throw new RuntimeException();
        }

        return ResponseEntity.ok(templateSpaces);

    }
    @GetMapping("/")
    public ResponseEntity<TemplateSpaceDTO> getUserCustomSpace(@RequestParam Long spaceId) throws IOException {

        // Call the service method to handle the file upload
        TemplateSpaceDTO templateSpaceDto = templateSpaceService.getTemplateSpaceWithMedia(spaceId);
        // Return the response entity with the updated CustomSpace and status
        return new ResponseEntity<>(templateSpaceDto, HttpStatus.OK);

    }
}

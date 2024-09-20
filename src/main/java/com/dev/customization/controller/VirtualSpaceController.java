package com.dev.customization.controller;

import com.dev.customization.entity.virtualspace.spaces.VirtualSpace;
import com.dev.customization.exceptions.ResourceNotFoundException;
import com.dev.customization.service.VirtualSpace.TemplateSpaceService;
import com.dev.customization.service.VirtualSpace.VirtualSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/virtual_space")
public class VirtualSpaceController {

    @Autowired
    private VirtualSpaceService virtualSpaceService;

    @Autowired
    private TemplateSpaceService templateSpaceService;

    // Get all available virtual spaces (template spaces)
//    @GetMapping("/templates")
//    public ResponseEntity<List<VirtualSpace>> getTemplates() {
//        try {
//            List<VirtualSpace> templates = templateSpaceService.getAllTemplates();
//            return ResponseEntity.ok(templates);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Collections.emptyList());
//        }
//    }

    // Get a virtual space template by ID
    @GetMapping("/template/{id}")
    public ResponseEntity<Optional<VirtualSpace>> getTemplateById(@RequestBody Long id) {
        try {
            Optional<VirtualSpace> template = templateSpaceService.getTemplateById(id);
            return ResponseEntity.ok(template);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
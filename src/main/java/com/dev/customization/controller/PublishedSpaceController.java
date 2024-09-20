package com.dev.customization.controller;


import com.dev.customization.entity.virtualspace.spaces.CustomSpace;
import com.dev.customization.payload.CustomSpaceDTO;
import com.dev.customization.service.VirtualSpace.CustomSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/public/")
public class PublishedSpaceController {

    @Autowired
    private CustomSpaceService customSpaceService;


    @GetMapping("/{customSpaceId}")
    public ResponseEntity<CustomSpaceDTO> getPublishedCustomSpace(@PathVariable Long customSpaceId) {
        try {
            // Fetch the CustomSpace using the customSpaceId
            CustomSpaceDTO customSpace = customSpaceService.getCustomSpaceWithMediaFiles(customSpaceId);
            if (customSpace == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(customSpace, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

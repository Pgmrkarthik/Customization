package com.dev.customization.service.VirtualSpace;


import com.dev.customization.entity.virtualspace.VirtualSpace;
import com.dev.customization.repository.virtual_space.VirtualSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TemplateSpaceService {

    @Autowired
    private VirtualSpaceRepository virtualSpaceRepository;


    public List<VirtualSpace> getAllTemplates() {
        return virtualSpaceRepository.findAll();
    }

    // Fetch a single template space by ID
    public Optional<VirtualSpace> getTemplateById(Long id) {

        return virtualSpaceRepository.findById(id);

    }


}

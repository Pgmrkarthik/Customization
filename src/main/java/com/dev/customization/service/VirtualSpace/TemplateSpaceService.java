package com.dev.customization.service.VirtualSpace;

import com.dev.customization.entity.virtualspace.*;
import com.dev.customization.entity.virtualspace.spaces.TemplateSpace;
import com.dev.customization.entity.virtualspace.spaces.VirtualSpace;
import com.dev.customization.payload.TemplateSpaceDTO;
import com.dev.customization.repository.virtual_space.*;
import com.dev.customization.repository.virtual_space.space.TemplateSpaceRepository;
import com.dev.customization.repository.virtual_space.space.VirtualSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class TemplateSpaceService {

    @Autowired
    private TemplateSpaceRepository templateSpaceRepository;

    @Autowired
    private VirtualSpaceRepository virtualSpaceRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private AudioRepository audioRepository;

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private TextFieldRepository textFieldRepository;


    public List<TemplateSpace> getAllTemplates() throws RuntimeException {
        return templateSpaceRepository.findAll();
    }

    // Fetch a single template space by ID
    public Optional<VirtualSpace> getTemplateById(Long id) {
        return virtualSpaceRepository.findById(id);
    }

    // Create a new TemplateSpace
    public TemplateSpace createTemplateSpace(TemplateSpace templateSpace) {
        return templateSpaceRepository.save(templateSpace);
    }

    // Get a TemplateSpace by ID
    public TemplateSpace getTemplateSpace(Long id) {
        return templateSpaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TemplateSpace not found"));
    }

    // Get all TemplateSpaces
    public List<TemplateSpace> getAllTemplateSpaces() {
        return templateSpaceRepository.findAll();
    }

    // Update an existing TemplateSpace
    public TemplateSpace updateTemplateSpace(Long id, TemplateSpace templateSpaceDetails) {
        TemplateSpace templateSpace = templateSpaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TemplateSpace not found"));

        templateSpace.setName(templateSpaceDetails.getName());
        templateSpace.setDescription(templateSpaceDetails.getDescription());
        // Update media or other fields if needed
        return templateSpaceRepository.save(templateSpace);
    }

    // Delete a TemplateSpace
    public void deleteTemplateSpace(Long id) {
        TemplateSpace templateSpace = templateSpaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TemplateSpace not found"));
        templateSpaceRepository.delete(templateSpace);
    }

    // Optionally: Get media associated with a TemplateSpace
    public TemplateSpaceDTO getTemplateSpaceWithMedia(Long templateSpaceId) {

        TemplateSpace templateSpace = templateSpaceRepository.findById(templateSpaceId)
                .orElseThrow(() -> new RuntimeException("TemplateSpace not found"));

        // Fetch related media (videos, images, audios, etc.)
        List<Video> videos = videoRepository.findByVirtualspaceId(templateSpaceId);
        List<Image> images = imageRepository.findByVirtualspaceId(templateSpaceId);
        List<Audio> audios = audioRepository.findByVirtualspaceId(templateSpaceId);
//        List<PDF> pdfs = pdfRepository.findByVirtualspaceId(templateSpaceId);
//        List<TextField> textFields = textFieldRepository.findByVirtualspaceId(templateSpaceId);



        return new TemplateSpaceDTO(templateSpaceId, templateSpace.getUrl(),videos,audios,images);
    }


}

package com.dev.customization.service.VirtualSpace;

import com.dev.customization.entity.virtualspace.*;
import com.dev.customization.repository.virtual_space.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VirtualSpaceService {

    @Autowired
    private VirtualSpaceRepository virtualSpaceRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private AudioRepository audioRepository;

    @Autowired
    private TextFieldRepository textFieldRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CustomSpaceRepository customSpaceRepository;

//    public VirtualSpace createVirtualSpace(VirtualSpace virtualSpace) {
//        return virtualSpaceRepository.save(virtualSpace);
//    }

    public CustomSpace createCustomSpace(CustomSpace customSpace) {


        return customSpaceRepository.save(customSpace);
    }

    public List<Video> getVideosForSpace(Long virtualSpaceId) {


        return videoRepository.findByCustomSpaceId(virtualSpaceId);
    }

    public List<Audio> getAudiosForSpace(Long virtualSpaceId) {

        return audioRepository.findByCustomSpaceId(virtualSpaceId);
    }

    public List<TextField> getTextFieldsForSpace(Long virtualSpaceId) {

        return textFieldRepository.findByCustomSpaceId(virtualSpaceId);
    }

    public List<Image> getImagesForSpace(Long virtualSpaceId) {
        return imageRepository.findByCustomSpaceId(virtualSpaceId);
    }

    // Additional methods for updating or deleting entities
}
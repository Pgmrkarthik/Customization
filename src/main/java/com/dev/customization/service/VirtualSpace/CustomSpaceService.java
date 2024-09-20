package com.dev.customization.service.VirtualSpace;


import com.dev.customization.Enum.MediaType;
import com.dev.customization.entity.User;
import com.dev.customization.entity.virtualspace.*;
import com.dev.customization.entity.virtualspace.spaces.CustomSpace;
import com.dev.customization.entity.virtualspace.spaces.PublishedSpace;
import com.dev.customization.entity.virtualspace.spaces.TemplateSpace;
import com.dev.customization.exceptions.ResourceNotFoundException;
import com.dev.customization.payload.CustomSpaceDTO;
import com.dev.customization.payload.MediaFileDTO;
import com.dev.customization.payload.PublishedSpaceDTO;
import com.dev.customization.payload.TemplateSpaceDTO;
import com.dev.customization.repository.user.UserRepository;
import com.dev.customization.repository.virtual_space.*;
import com.dev.customization.repository.virtual_space.space.CustomSpaceRepository;
import com.dev.customization.repository.virtual_space.space.PublishedSpaceRepository;
import com.dev.customization.repository.virtual_space.space.TemplateSpaceRepository;
import com.dev.customization.repository.virtual_space.space.VirtualSpaceRepository;
import com.dev.customization.service.AWS.S3Service;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomSpaceService {


    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private AudioRepository audioRepository;

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CustomSpaceRepository customSpaceRepository;

    @Autowired
    private VirtualSpaceRepository virtualSpaceRepository;

    @Autowired
    private TemplateSpaceService templateSpaceService;

    @Autowired
    private TextFieldRepository textFieldRepository;

    @Autowired
    private TemplateSpaceRepository templateSpaceRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private PublishedSpaceRepository publishedSpaceRepository;

    public CustomSpaceDTO createCustomSpaceFromTemplate(Long templateId) throws ResourceNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(ResourceNotFoundException::new);


        // Fetch the TemplateSpace by ID
       // TemplateSpaceDTO templateSpaceDto = templateSpaceService.getTemplateSpaceWithMedia(templateId);

        TemplateSpace templateSpace = templateSpaceRepository.findById(templateId).orElseThrow(ResourceNotFoundException::new);

        // Create a new CustomSpace and copy data from the TemplateSpace
        CustomSpace customSpace = new CustomSpace();
        customSpace.setTemplateSpace(templateSpace);  // Link the template space

        // Set the user ID for the CustomSpace
        customSpace.setUser(user); // Set the user ID

        // Copy relevant fields from TemplateSpace to CustomSpace
        customSpace.setName(templateSpace.getName());
        customSpace.setDescription(templateSpace.getDescription()); // Fixed description

        CustomSpace savedCustomSpace = customSpaceRepository.save(customSpace);
        // Copy media (if needed)
        // Copy media files from TemplateSpace to CustomSpace
        copyVideosFromTemplate(templateSpace, savedCustomSpace);
        copyAudiosFromTemplate(templateSpace, savedCustomSpace);
        copyPDFsFromTemplate(templateSpace, savedCustomSpace);
        copyImagesFromTemplate(templateSpace, savedCustomSpace);
       // customSpace.setTextFields(new ArrayList<>(templateSpace.getTextFields())); // Copy all text fields

        // Save the new CustomSpace to the database
       // customSpaceRepository.save(customSpace);

        return this.getCustomSpaceWithMediaFiles(savedCustomSpace.getId());
    }

    // Find custom space with space id Long id

    public CustomSpaceDTO getCustomSpaceWithMediaFiles(Long customSpaceId) {
        // Fetch the CustomSpace by ID
        CustomSpace customSpace = customSpaceRepository.findById(customSpaceId)
                .orElseThrow(() -> new RuntimeException("CustomSpace not found"));

        System.out.println("custom space"+ customSpace);

        // Fetch and set media files (Videos, Audios, Images, PDFs)
        List<Video> videos = videoRepository.findByVirtualspaceId(customSpaceId);
        List<Audio> audios = audioRepository.findByVirtualspaceId(customSpaceId);
        List<Image> images = imageRepository.findByVirtualspaceId(customSpaceId);

        // Return CustomSpace with media files
        return new CustomSpaceDTO(customSpace.getId(), customSpace.getUser().getId(), customSpace.getTemplateSpace().getUrl(), videos, audios, images);
    }


//    public PublishedSpaceDTO getPublishedSpaceWithMediaFiles(Long customSpaceId) {
//        // Fetch the CustomSpace by ID
//        CustomSpace customSpace = customSpaceRepository.findById(customSpaceId)
//                .orElseThrow(() -> new RuntimeException("CustomSpace not found"));
//
//        System.out.println("custom space"+ customSpace);
//
//        // Fetch and set media files (Videos, Audios, Images, PDFs)
//        List<Video> videos = videoRepository.findByVirtualspaceId(customSpaceId);
//        List<Audio> audios = audioRepository.findByVirtualspaceId(customSpaceId);
//        List<Image> images = imageRepository.findByVirtualspaceId(customSpaceId);
//
//        // Return CustomSpace with media files
//        return new PublishedSpaceDTO(customSpaceId, customSpace.getTemplateSpace().getUrl(), videos, audios, images);
//    }










    public List<CustomSpace> getAllCustomSpaceByUser() throws ResourceNotFoundException{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(ResourceNotFoundException::new);

        List<CustomSpace> customSpaces = customSpaceRepository.findByUser(user);

        System.out.println(customSpaces);

        return customSpaces;

    }


    // Delete CustomSpace
    public void deleteCustomSpace(Long id) {
        customSpaceRepository.deleteById(id);
    }

    @Transactional
    public CustomSpaceDTO uploadFile(MultipartFile file, Long customSpaceId, int position, MediaType mediaType) throws IOException {
        // Upload the file to S3 and get the media URL
        String mediaUrl = s3Service.newUploadFile(file);

        System.out.println("Media Url"+mediaUrl);

        // Retrieve the CustomSpace


        CustomSpace customSpace = customSpaceRepository.findById(customSpaceId).orElseThrow(ResourceNotFoundException::new);

        // Check if the media already exists at the specified position and update or create a new entry
        if (mediaType == MediaType.VIDEO) {

            Video video = videoRepository.findByVirtualspaceIdAndPosition(customSpaceId, position);

            System.out.println(video);
            // Check if there is already a video at the specified position
            if (video != null) {
                // Update existing video
                video.setTitle(file.getOriginalFilename());
                video.setVideoUrl(mediaUrl);
                videoRepository.save(video);  // Save the updated video
            } else {
                // Create a new Video object and add it to the media table
                Video newVideo = new Video();
                newVideo.setTitle(file.getOriginalFilename());
                newVideo.setVideoUrl(mediaUrl);
                newVideo.setPosition(position);
                newVideo.setVirtualspace(customSpace);  // Link to CustomSpace
                videoRepository.save(newVideo);  // Save the new video
            }
        } else if (mediaType == MediaType.PDF) {
            // Check if there is already a PDF at the specified position
            if (position < customSpace.getPdfs().size()) {
                // Update existing PDF
                PDF existingPDF = customSpace.getPdfs().get(position);
                existingPDF.setTitle(file.getOriginalFilename());
                existingPDF.setUrl(mediaUrl);
                pdfRepository.save(existingPDF);  // Save the updated PDF
            } else {
                // Create a new PDF object and add it to the media table
                PDF newPDF = new PDF();
                newPDF.setTitle(file.getOriginalFilename());
                newPDF.setUrl(mediaUrl);
                newPDF.setVirtualspace(customSpace);  // Link to CustomSpace
                pdfRepository.save(newPDF);  // Save the new PDF
            }
        } else if (mediaType == MediaType.AUDIO) {
            // Check if there is already an audio file at the specified position
            if (position < customSpace.getAudios().size()) {
                // Upojodate existing audio
                Audio existingAudio = customSpace.getAudios().get(position);
                existingAudio.setTitle(file.getOriginalFilename());
                existingAudio.setAudioUrl(mediaUrl);
                audioRepository.save(existingAudio);  // Save the updated audio
            } else {
                // Create a new Audio object and add it to the media table
                Audio newAudio = new Audio();
                newAudio.setTitle(file.getOriginalFilename());
                newAudio.setAudioUrl(mediaUrl);
                newAudio.setVirtualspace(customSpace);  // Link to CustomSpace
                audioRepository.save(newAudio);  // Save the new audio
            }
        } else if (mediaType == MediaType.IMAGE) {
            // Check if there is already an image at the specified position
            if (position < customSpace.getImages().size()) {
                // Update existing image
                Image existingImage = customSpace.getImages().get(position);
                existingImage.setTitle(file.getOriginalFilename());
                existingImage.setImageUrl(mediaUrl);
                imageRepository.save(existingImage);  // Save the updated image
            } else {
                // Create a new Image object and add it to the media table
                Image newImage = new Image();
                newImage.setTitle(file.getOriginalFilename());
                newImage.setImageUrl(mediaUrl);
                newImage.setVirtualspace(customSpace);  // Link to CustomSpace
                imageRepository.save(newImage);  // Save the new image
            }
        }


        // Return the updated CustomSpace (if needed)
        return this.getCustomSpaceWithMediaFiles(customSpaceId);
    }


    //Publish the content



    public String publishCustomSpace(Long customSpaceId, String sourceUrl) {
        // Fetch the CustomSpace by ID
        CustomSpace customSpace = customSpaceRepository.findById(customSpaceId)
                .orElseThrow(() -> new RuntimeException("CustomSpace not found"));
        // Check if the space is already published
        // Generate a public URL (for example, using a UUID)

        // Save the published space details to a new table/entity if required
//        PublishedSpace publishedSpace = new PublishedSpace();
//        publishedSpace.setCustomSpace(customSpace);
//        publishedSpace.setPublicUrl(publicUrl);
//        publishedSpaceRepository.save(publishedSpace);
        return generatePublicUrl(customSpaceId);
    }

    // Generate a unique public URL for the CustomSpace
    private String generatePublicUrl(Long customSpaceId) {

        // Generate a unique identifier
        return   s3Service.copyFolder(customSpaceId);// Construct the full public URL
    }

    public CustomSpace getPublishedCustomSpaceById(Long customSpaceId) {
        // Fetch the CustomSpace by ID and check if it is published

        return customSpaceRepository.findById(customSpaceId)
                .orElse(null); // Return null if not found or not published
    }


    private void copyVideosFromTemplate(TemplateSpace templateSpace, CustomSpace customSpace) {
        // Fetch all videos linked to the template space
        List<Video> templateVideos = videoRepository.findByVirtualspaceId(templateSpace.getId());

        // Create new video objects and link them to the custom space
        List<Video> customVideos = templateVideos.stream().map(video -> {
            Video newVideo = new Video();
            newVideo.setVirtualspace(customSpace);  // Link the new CustomSpace
            newVideo.setTitle(video.getTitle());
            newVideo.setVideoUrl(video.getVideoUrl());
            return newVideo;
        }).collect(Collectors.toList());

        // Save the new videos linked to the CustomSpace
        videoRepository.saveAll(customVideos);
    }

    private void copyAudiosFromTemplate(TemplateSpace templateSpace, CustomSpace customSpace) {
        // Fetch all audios linked to the template space
        List<Audio> templateAudios = audioRepository.findByVirtualspaceId(templateSpace.getId());

        // Create new audio objects and link them to the custom space
        List<Audio> customAudios = templateAudios.stream().map(audio -> {
            Audio newAudio = new Audio();
            newAudio.setVirtualspace(customSpace);  // Link the new CustomSpace
            newAudio.setTitle(audio.getTitle());
            newAudio.setAudioUrl(audio.getAudioUrl());
            return newAudio;
        }).collect(Collectors.toList());

        // Save the new audios linked to the CustomSpace
        audioRepository.saveAll(customAudios);
    }

    private void copyPDFsFromTemplate(TemplateSpace templateSpace, CustomSpace customSpace) {
        // Fetch all PDFs linked to the template space
        List<PDF> templatePDFs = pdfRepository.findByVirtualspaceId(templateSpace.getId());

        // Create new PDF objects and link them to the custom space
        List<PDF> customPDFs = templatePDFs.stream().map(pdf -> {
            PDF newPDF = new PDF();
            newPDF.setVirtualspace(customSpace);  // Link the new CustomSpace
            newPDF.setTitle(pdf.getTitle());
            newPDF.setUrl(pdf.getUrl());
            return newPDF;
        }).collect(Collectors.toList());

        // Save the new PDFs linked to the CustomSpace
        pdfRepository.saveAll(customPDFs);
    }

    private void copyImagesFromTemplate(TemplateSpace templateSpace, CustomSpace customSpace) {
        // Fetch all images linked to the template space
        List<Image> templateImages = imageRepository.findByVirtualspaceId(templateSpace.getId());

        // Create new Image objects and link them to the custom space
        List<Image> customImages = templateImages.stream().map(image -> {
            Image newImage = new Image();
            newImage.setVirtualspace(customSpace);  // Link the new CustomSpace
            newImage.setTitle(image.getTitle());
            newImage.setImageUrl(image.getImageUrl());
            return newImage;
        }).collect(Collectors.toList());

        // Save the new Images linked to the CustomSpace
        imageRepository.saveAll(customImages);
    }

}

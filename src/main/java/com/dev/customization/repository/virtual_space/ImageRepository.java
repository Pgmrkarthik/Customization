package com.dev.customization.repository.virtual_space;

import com.dev.customization.entity.virtualspace.Audio;
import com.dev.customization.entity.virtualspace.Image;
import com.dev.customization.entity.virtualspace.spaces.VirtualSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByVirtualspace(VirtualSpace virtualspace);

    // Find all audio files for a specific virtual space
    List<Image> findByVirtualspaceId(Long virtualSpaceId);

}
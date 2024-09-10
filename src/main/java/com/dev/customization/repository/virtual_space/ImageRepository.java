package com.dev.customization.repository.virtual_space;

import com.dev.customization.entity.virtualspace.Audio;
import com.dev.customization.entity.virtualspace.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByCustomSpaceId(Long CustomSpaceId);
}
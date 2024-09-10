package com.dev.customization.repository.virtual_space;

import com.dev.customization.entity.virtualspace.Audio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudioRepository extends JpaRepository<Audio, Long> {

    List<Audio> findByCustomSpaceId(Long virtualSpaceId);
}
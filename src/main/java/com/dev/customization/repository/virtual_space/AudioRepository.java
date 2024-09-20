package com.dev.customization.repository.virtual_space;

import com.dev.customization.entity.virtualspace.Audio;
import com.dev.customization.entity.virtualspace.spaces.VirtualSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudioRepository extends JpaRepository<Audio, Long> {

    // Find all audio files for a specific virtual space
    List<Audio> findByVirtualspace(VirtualSpace virtualSpace);

    // Find all audio files for a specific virtual space
    List<Audio> findByVirtualspaceId(Long virtualSpaceId);


}
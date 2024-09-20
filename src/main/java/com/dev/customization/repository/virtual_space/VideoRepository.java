package com.dev.customization.repository.virtual_space;

import com.dev.customization.entity.virtualspace.Video;
import com.dev.customization.entity.virtualspace.spaces.VirtualSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {


    List<Video> findByVirtualspace(VirtualSpace virtualspace);

    // Find all audio files for a specific virtual space
    List<Video> findByVirtualspaceId(Long virtualSpaceId);

    Video findByVirtualspaceIdAndPosition(long virtualSpaceId, long position);
}
package com.dev.customization.repository.virtual_space;


import com.dev.customization.entity.virtualspace.PDF;
import com.dev.customization.entity.virtualspace.spaces.VirtualSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PdfRepository extends JpaRepository<PDF, Long> {

    List<PDF> findByVirtualspace(VirtualSpace virtualspace);

    // Find all audio files for a specific virtual space
    List<PDF> findByVirtualspaceId(Long virtualSpaceId);

}

package com.dev.customization.repository.virtual_space;

import com.dev.customization.entity.virtualspace.TextField;
import com.dev.customization.entity.virtualspace.spaces.VirtualSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextFieldRepository extends JpaRepository<TextField, Long> {
    List<TextField> findByVirtualspace(VirtualSpace virtualSpace);

    // Find all audio files for a specific virtual space
    List<TextField> findByVirtualspaceId(Long virtualSpaceId);
}
package com.dev.customization.repository.virtual_space.space;


import com.dev.customization.entity.User;
import com.dev.customization.entity.virtualspace.spaces.CustomSpace;
import com.dev.customization.entity.virtualspace.spaces.PublishedSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublishedSpaceRepository extends JpaRepository<PublishedSpace, Long> {
    // Find all published spaces for a specific user
    List<PublishedSpace> findByCustomSpace(CustomSpace customSpace);

    PublishedSpace findByPublicUrl(String publicUrl);

    // Additional queries to fetch published spaces by UUID, etc.
}

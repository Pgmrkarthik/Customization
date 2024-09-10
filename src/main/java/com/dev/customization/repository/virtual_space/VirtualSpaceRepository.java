package com.dev.customization.repository.virtual_space;

import com.dev.customization.entity.virtualspace.VirtualSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirtualSpaceRepository extends JpaRepository<VirtualSpace, Long> {
    // Custom query methods (if needed)
}

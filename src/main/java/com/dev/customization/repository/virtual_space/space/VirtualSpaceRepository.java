package com.dev.customization.repository.virtual_space.space;

import com.dev.customization.entity.virtualspace.spaces.VirtualSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirtualSpaceRepository extends JpaRepository<VirtualSpace, Long> {


}

package com.dev.customization.repository.virtual_space.space;

import com.dev.customization.entity.User;
import com.dev.customization.entity.virtualspace.spaces.CustomSpace;
import com.dev.customization.entity.virtualspace.spaces.TemplateSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomSpaceRepository extends JpaRepository<CustomSpace, Long> {

    // Find all custom spaces created from a specific template
    List<CustomSpace> findByTemplateSpace(TemplateSpace templateSpace);

    // Find all custom spaces created by a specific user
    List<CustomSpace> findByUser(User user);

}

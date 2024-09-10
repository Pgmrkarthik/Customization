package com.dev.customization.repository.virtual_space;

import com.dev.customization.entity.virtualspace.CustomSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomSpaceRepository extends JpaRepository<CustomSpace, Long> {

    @Override
    Optional<CustomSpace> findById(Long aLong);
}

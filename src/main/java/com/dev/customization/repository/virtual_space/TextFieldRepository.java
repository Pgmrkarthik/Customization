package com.dev.customization.repository.virtual_space;

import com.dev.customization.entity.virtualspace.TextField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextFieldRepository extends JpaRepository<TextField, Long> {
    List<TextField> findByCustomSpaceId(Long virtualSpaceId);
}
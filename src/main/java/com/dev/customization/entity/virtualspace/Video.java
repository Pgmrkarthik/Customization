package com.dev.customization.entity.virtualspace;

import com.dev.customization.entity.virtualspace.spaces.VirtualSpace;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int position;

    private String videoUrl; // URL of the video

    private String title;

    @ManyToOne
    @JoinColumn(name = "virtual_space_id")
    @JsonIgnore
    private VirtualSpace virtualspace;  // Reference to the space (Template, Custom, or Published)

    // Getters and Setters
}
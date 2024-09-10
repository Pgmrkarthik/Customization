package com.dev.customization.entity.virtualspace;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String audioUrl; // URL of the audio
    private String title;

    @ManyToOne
    @JoinColumn(name = "custom_space_id")
    private CustomSpace customSpace;

    // Getters and Setters
}
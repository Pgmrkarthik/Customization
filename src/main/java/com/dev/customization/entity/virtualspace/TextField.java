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
public class TextField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; // The text content of the field
    private String label;

    @ManyToOne
    @JoinColumn(name = "custom_space_id")
    private CustomSpace customSpace;

    // Getters and Setters
}

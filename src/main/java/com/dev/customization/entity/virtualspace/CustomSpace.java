package com.dev.customization.entity.virtualspace;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "custom_space")
public class CustomSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    private String name; // Name of the booth
    private String description; // Description of the booth

    @ManyToOne
    @JoinColumn(name = "template_space_id")
    private VirtualSpace TemplateID;

    @OneToMany(mappedBy = "customSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos = new ArrayList<>();

    @OneToMany(mappedBy = "customSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "customSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Audio> audios = new ArrayList<>();

    @OneToMany(mappedBy = "customSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TextField> textFields = new ArrayList<>();
}

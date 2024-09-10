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
@Table(name = "template_space")
public class VirtualSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url; // URL for the virtual space
    private String description;

//    @OneToMany(mappedBy = "virtualSpace", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Video> videos = new ArrayList<>();
//
//    @OneToMany(mappedBy = "virtualSpace", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Image> images = new ArrayList<>();
//
//    @OneToMany(mappedBy = "virtualSpace", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Audio> audios = new ArrayList<>();
//
//    @OneToMany(mappedBy = "virtualSpace", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TextField> textFields = new ArrayList<>();
}

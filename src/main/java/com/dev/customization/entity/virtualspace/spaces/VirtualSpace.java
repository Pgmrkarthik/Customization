package com.dev.customization.entity.virtualspace.spaces;



import com.dev.customization.entity.virtualspace.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
// This is template space It contains template images and videos
public class VirtualSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "virtualspace")
   // mappedBy is the Attribute name of videos entity
    private List<Video> videos = new ArrayList<>();

    @OneToMany(mappedBy = "virtualspace")

    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "virtualspace")

    private List<Audio> audios = new ArrayList<>();

    @OneToMany(mappedBy = "virtualspace")
    private List<PDF> pdfs = new ArrayList<>();

    @OneToMany(mappedBy = "virtualspace")
    private List<TextField> textFields = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}

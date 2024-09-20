package com.dev.customization.entity.virtualspace.spaces;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class PublishedSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false, unique = true)
    private String publicUrl;

    @OneToOne
    @JoinColumn(name = "custom_space_id")
    private CustomSpace customSpace;


}

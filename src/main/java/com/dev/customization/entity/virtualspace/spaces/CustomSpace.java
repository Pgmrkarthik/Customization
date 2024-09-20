package com.dev.customization.entity.virtualspace.spaces;

import com.dev.customization.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class CustomSpace extends VirtualSpace{


    private String name; // Name of the booth



    private String description; // Description of the booth

    // Many custom spaces are created from one template space
    @ManyToOne
    @JoinColumn(name = "template_space_id")
    private TemplateSpace templateSpace;

    // Many custom spaces are created by one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // One-to-one relationship with PublishedSpace
    @OneToOne(mappedBy = "customSpace")
    private PublishedSpace publishedSpace;



}

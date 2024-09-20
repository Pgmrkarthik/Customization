package com.dev.customization.entity.virtualspace.spaces;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class TemplateSpace extends VirtualSpace {

    private String url;

    private String name;

    private String description;

}

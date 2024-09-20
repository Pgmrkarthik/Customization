package com.dev.customization.payload;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MediaFileDTO {
    private Long id;
    private String url;
    private String title;
    private int position;
}

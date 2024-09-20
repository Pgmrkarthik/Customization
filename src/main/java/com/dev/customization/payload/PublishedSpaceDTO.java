package com.dev.customization.payload;


import com.dev.customization.entity.virtualspace.Audio;
import com.dev.customization.entity.virtualspace.Image;
import com.dev.customization.entity.virtualspace.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class PublishedSpaceDTO {

    private Long customSpaceId;
    private String url;


    private List<Video> videosList;
    private List<Audio> audioList;
    private List<Image> imageList;

}

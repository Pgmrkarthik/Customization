package com.dev.customization.payload;


import lombok.*;

@Data@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class PublishSpaceRequestDTO {

    private Long spaceId;

    private String Url;
}

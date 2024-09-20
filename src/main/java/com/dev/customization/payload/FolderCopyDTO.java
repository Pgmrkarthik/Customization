package com.dev.customization.payload;



import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
public class FolderCopyDTO {

    private String SourceId;
    private String DestinationId;
}

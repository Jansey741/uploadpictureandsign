package hu.ponte.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageMetaDto {
    private String id;
    private String name;
    private String mimeType;
    private long size;
    private String digitalSign;
}

package hu.ponte.hr.controller;


import hu.ponte.hr.dto.ImageMetaDto;
import hu.ponte.hr.entity.ImageMeta;
import hu.ponte.hr.services.ImageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("api/images")
public class ImagesController {

    @Autowired
    private ImageStore imageStore;

    @GetMapping("meta")
    public List<ImageMetaDto> listImages() {

        List<ImageMeta> imageMetas = imageStore.getImages();
        List<ImageMetaDto> imageMetaDtos = new ArrayList<>();

        for (ImageMeta imageMeta: imageMetas) {
            ImageMetaDto imageMetaDto = imageMetaMapper(imageMeta);
            imageMetaDtos.add(imageMetaDto);
        }

        return imageMetaDtos;
    }

    private static ImageMetaDto imageMetaMapper(ImageMeta imageMeta) {
        return ImageMetaDto.builder()
                        .size(imageMeta.getSize())
                        .name(imageMeta.getName())
                        .digitalSign(imageMeta.getDigitalSign())
                        .mimeType(imageMeta.getMimeType())
                        .id(imageMeta.getId().toString())
                        .build();
    }

    @GetMapping("preview/{id}")
    public byte[] getImage(@PathVariable("id") Integer id, HttpServletResponse response) {
        return imageStore.getImageById(id).getImageData();
	}

}

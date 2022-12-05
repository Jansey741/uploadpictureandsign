package hu.ponte.hr.controller.upload;

import hu.ponte.hr.entity.Image;
import hu.ponte.hr.entity.ImageMeta;
import hu.ponte.hr.services.ImageStore;
import hu.ponte.hr.services.SignService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@Component
@RequestMapping("api/file")
@Slf4j
public class UploadController
{
    @Autowired
    private SignService signService;

    @Autowired
    private ImageStore imageStore;

    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public Image handleFormUpload(@RequestParam("file") MultipartFile file) throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, SignatureException,
            InvalidKeyException {

        Tika tika = new Tika();
        String mimeType = tika.detect(file.getInputStream());


        ImageMeta imageMeta = ImageMeta.builder()
                        .size(file.getSize())
                        .name(file.getOriginalFilename())
                        .digitalSign(signService.signPicture(file))
                        .mimeType(mimeType)
                        .build();

        Image image = Image.builder()
                .imageData(file.getBytes())
                .imageMeta(imageMeta)
                .build();
        log.info("{} is persisted, Meta infos are created"
                , file.getOriginalFilename());
        return imageStore.save(image);
    }
}

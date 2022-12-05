package hu.ponte.hr.services;

import hu.ponte.hr.entity.Image;
import hu.ponte.hr.entity.ImageMeta;
import hu.ponte.hr.repository.ImageMetaRepository;
import hu.ponte.hr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageStore {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private ImageMetaRepository imageMetaRepository;

    public Image save(Image image){
        return repository.save(image);
    }

    public List<ImageMeta> getImages(){
        return imageMetaRepository.findAll();
    }

    public Image getImageById(Integer id){
        return Optional.of(repository.findByImageMetaId(id))
                .orElse(new Image());
    }



}

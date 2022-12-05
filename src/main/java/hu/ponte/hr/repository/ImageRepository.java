package hu.ponte.hr.repository;

import hu.ponte.hr.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    public Image findByImageMetaId(Integer metaId);
}

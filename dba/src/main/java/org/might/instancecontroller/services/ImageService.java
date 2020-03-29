package org.might.instancecontroller.services;

import org.might.instancecontroller.dba.entity.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Optional<Image> getImageById(Long id);
    void saveImage(Image image);
    void deleteImage(Image image);
    List<Image> getAll();
}

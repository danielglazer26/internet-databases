package bednarz.glazer.iwaniak.estate.portal.database.services;

import bednarz.glazer.iwaniak.estate.portal.database.model.Photo;
import bednarz.glazer.iwaniak.estate.portal.database.repositories.PhotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PhotoService {
    private final PhotoRepository repository;

    @Autowired
    public PhotoService(PhotoRepository repository) {
        this.repository = repository;
    }

    public Optional<Photo> createPhoto(byte[] photo, Long announcementId) {
        return Optional.of(repository.save(new Photo(0L, announcementId, photo)));
    }

    public InputStream getPhoto(Long photoId) throws Exception {
        var photo = repository.getPhotoByPhotoId(photoId);
        if(photo.isPresent())
            return new ByteArrayInputStream(photo.get().getPictureBytes());
        else
            throw new Exception("There is no picture with such id in database");
    }

    public List<Photo> findAll() {
        return repository.findAll();
    }

    public List<Photo> getPhotosByAnnouncementId(Long announcementId) {
        return repository.getPhotosByAnnouncementId(announcementId);
    }
    public List<Long> getPhotosIdsByAnnouncementId(Long announcementId) {
        return repository.getPhotosIdsByAnnouncementId(announcementId);
    }

    public void removePhoto(Photo photo) {
        repository.delete(photo);
    }

    public void removePhotos(List<Photo> photo) {
        repository.deleteAll(photo);
    }
}

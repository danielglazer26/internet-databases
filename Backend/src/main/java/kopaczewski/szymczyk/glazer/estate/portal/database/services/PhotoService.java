package kopaczewski.szymczyk.glazer.estate.portal.database.services;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Photo;
import kopaczewski.szymczyk.glazer.estate.portal.database.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public InputStream getPhoto(Long photoId){
        return new ByteArrayInputStream(repository.getPhotoByPhotoId(photoId).getPictureBytes());
    }

    public List<Photo> findAll() {
        return repository.findAll();
    }

    public List<Photo> getPhotosByAnnouncementId(Long announcementId) {
        return repository.getPhotosByAnnouncementId(announcementId);
    }

    public void removePhoto(Photo photo) {
        repository.delete(photo);
    }

    public void removePhotos(List<Photo> photo) {
        repository.deleteAll(photo);
    }
}

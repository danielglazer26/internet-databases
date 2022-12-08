package kopaczewski.szymczyk.glazer.estate.portal.database.repositories;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @SuppressWarnings("NullableProblems")
    @Override
    <S extends Photo> S save(S entity);

    @Query("select p from Photo p where p.announcementId = :announcementId")
    List<Photo> getPhotosByAnnouncementId(@Param("announcementId") Long announcementId);
    @Query("select p.photoId from Photo p where p.announcementId = :announcementId")
    List<Long> getPhotosIdsByAnnouncementId(@Param("announcementId") Long announcementId);

    @Query("select p from Photo p where p.photoId = :photoId")
    Optional<Photo> getPhotoByPhotoId(@Param("photoId") Long photoId);
}

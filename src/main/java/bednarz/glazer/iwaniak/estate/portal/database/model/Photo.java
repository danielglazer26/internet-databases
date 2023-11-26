package bednarz.glazer.iwaniak.estate.portal.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;
    private Long announcementId;
    @Lob
    private byte[] pictureBytes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Photo photo = (Photo) o;
        return photoId != null && Objects.equals(photoId, photo.photoId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

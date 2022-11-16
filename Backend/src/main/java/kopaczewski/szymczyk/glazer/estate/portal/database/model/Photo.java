package kopaczewski.szymczyk.glazer.estate.portal.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long photoId;
    private String fileLocation;
    @ManyToOne
    @JoinColumn(name = "fk_announcementId")
    private Announcement announcementId;
}

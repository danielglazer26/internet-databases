package kopaczewski.szymczyk.glazer.estate.portal.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementId;
    @Column(nullable = false)
    private String title, additionalDescription;
    private Long ownerId;
    //apartment address
    @Column(nullable = false)
    private Integer apartmentNumber;
    @Column(nullable = false)
    private String street, city;
    //apartment data
    @Column(nullable = false)
    private Integer costPerMonth, rent, deposit;
    // @Size(0,10)
    @Column(nullable = false)
    private Integer roomNumber;
    @Column(nullable = false)
    private Double area;
    @OneToMany(mappedBy = "announcementId")
    private Set<Photo> photos;
}

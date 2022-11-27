package kopaczewski.szymczyk.glazer.estate.portal.database.model.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private AnnouncementType announcementType;
    private String ownerLogin;
    //apartment address
    private String apartmentNumber;
    @Column(nullable = false)
    private String street, city;
    @Column(nullable = false)
    private ProvinceNameEnum province;
    //apartment data
    @Column(nullable = false)
    private Integer costPerMonth, rent;
    private Integer deposit;
    private Integer roomNumber;
    @Column(nullable = false)
    private Double area;
}

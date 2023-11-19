package bednarz.glazer.iwaniak.estate.portal.database.model.announcement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String title;
    @Column(nullable = false,length = 1000)
    private String additionalDescription;
    private AnnouncementType announcementType;
    private String ownerLogin;
    private Long coverPhotoId;
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

package kopaczewski.szymczyk.glazer.estate.portal.controller.authenticated;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Photo;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AnnouncementRequest {
    private String title, additionalDescription;
    private String ownerLogin, street, city;
    private Integer apartmentNumber, costPerMonth, rent, deposit, roomNumber;
    private Double area;
    private Set<Photo> photos;
}

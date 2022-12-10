package kopaczewski.szymczyk.glazer.estate.portal.controller.unauthenticated;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.announcement.Announcement;
import lombok.Getter;


@Getter
public class AnnouncementRespond {
    private Long announcementId;

    private String title, additionalDescription;
    private String announcementType;
    private String ownerLogin;
    private Long coverPhotoId;
    //apartment address
    private String apartmentNumber;

    private String street, city;

    private String province;
    //apartment data
    private Integer costPerMonth, rent;
    private Integer deposit;
    private Integer roomNumber;
    private Double area;

    public AnnouncementRespond(Announcement announcement) {
        announcementId = announcement.getAnnouncementId();
        this.title = announcement.getTitle();
        this.additionalDescription = announcement.getAdditionalDescription();
        this.announcementType =announcement.getAnnouncementType().toString();
        this.ownerLogin = announcement.getOwnerLogin();
        this.coverPhotoId = announcement.getCoverPhotoId();
        this.apartmentNumber = announcement.getApartmentNumber();
        this.street = announcement.getStreet();
        this.city = announcement.getCity();
        this.province = announcement.getProvince().toString();
        this.costPerMonth = announcement.getCostPerMonth();
        this.rent = announcement.getRent();
        this.deposit = announcement.getDeposit();
        this.roomNumber = announcement.getRoomNumber();
        this.area = announcement.getArea();
    }
}

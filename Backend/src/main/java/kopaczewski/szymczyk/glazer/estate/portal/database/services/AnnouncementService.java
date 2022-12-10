package kopaczewski.szymczyk.glazer.estate.portal.database.services;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.announcement.Announcement;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.announcement.AnnouncementType;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.announcement.ProvinceNameEnum;
import kopaczewski.szymczyk.glazer.estate.portal.database.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    private final PersonService personService;

    @Autowired
    public AnnouncementService(AnnouncementRepository announcementRepository, PersonService personService) {
        this.announcementRepository = announcementRepository;
        this.personService = personService;
    }

    private static boolean checkIfCorrectLoginUpdateAnnouncement(Announcement announcement, Announcement annId) {
        return !annId.getOwnerLogin().equals(announcement.getOwnerLogin());
    }

    public Optional<Announcement> createNewAnnouncement(
            String title, String additionalDescription, Integer announcementType, String personLogin,
            String city, String street, String apartmentNumber,
            Integer costPerMonth, Integer rent, Integer deposit,
            Integer roomNumber, Double area) {
        var person = personService.getPersonByLogin(personLogin);
        return person.map(value -> announcementRepository.save(
                new Announcement(
                        0L,
                        title, additionalDescription, AnnouncementType.values()[announcementType], value.getLogin(),
                        0L, apartmentNumber, street, city, ProvinceNameEnum.POMERANIAN,
                        costPerMonth, rent, deposit,
                        roomNumber, area
                )
        ));
    }

    public Optional<Announcement> createNewAnnouncement(Announcement announcement) throws Exception {
        if (announcement.getOwnerLogin() == null)
            throw new Exception("This announcement owner isn't assign");
        var person = personService.getPersonByLogin(announcement.getOwnerLogin());
        return person.map(value -> announcementRepository.save(announcement));
    }

    public List<Announcement> getAll(Integer limit, Integer offset) {
        return announcementRepository.findAll(limit, offset);
    }

    List<Announcement> getAll() {
        return announcementRepository.findAll();
    }

    public List<Announcement> getFiltered(
            Integer minCost, Integer maxCost,
            Double minArea, Double maxArea,
            Integer roomNumber,
            String city, String street, String apartmentNumber,
            Integer announcementType,
            Integer limit, Integer offset) {

        return announcementRepository.findFiltered(
                minCost, maxCost,
                minArea, maxArea,
                roomNumber,
                city,
                street,
                apartmentNumber,
                announcementType,
                limit, offset).stream().filter(announcement -> announcement.getCostPerMonth()>=0).toList();

    }

    public void updateAnnouncement(Announcement announcement) throws Exception {
        if (announcement.getAnnouncementId().equals(-1L))
            throw new Exception("Wrong id");
        var annId = announcementRepository.findById(announcement.getAnnouncementId());
        if (annId.isEmpty()) {
            throw new Exception("There is no such announcement in database");
        }
        if (checkIfCorrectLoginUpdateAnnouncement(announcement, annId.get())) {
            throw new Exception("You cant change owner of announcement");
        }
        announcementRepository.updateAnnouncement(
                announcement.getAnnouncementId(),
                announcement.getTitle(),
                announcement.getAdditionalDescription(),
                announcement.getAnnouncementType(),
                announcement.getCoverPhotoId(),
                announcement.getApartmentNumber(),
                announcement.getStreet(),
                announcement.getCity(),
                announcement.getProvince(),
                announcement.getCostPerMonth(),
                announcement.getRent(),
                announcement.getDeposit(),
                announcement.getRoomNumber(),
                announcement.getArea());
    }

    public void removeAnnouncement(Long announcementId) throws Exception {
        var annInDb = announcementRepository.findAnnouncementByAnnouncementId(announcementId);
        if (annInDb.isPresent()) {
            announcementRepository.delete(annInDb.get());
        } else {
            throw new Exception("there is no such announcement in database");
        }
    }

    public void removeAnnouncements(List<Announcement> announcementList) {
        announcementRepository.deleteAll(announcementList);
    }

    public Optional<Long> getAnnouncementCoverPhotoId(Long announcementId) {
        return announcementRepository.getCoverPhotoIdByAnnouncementId(announcementId);
    }

    public List<Announcement> getAnnouncementsByOwnerLogin(String ownerLogin) {
        return announcementRepository.getAnnouncementsByOwnerLogin(ownerLogin);
    }
}
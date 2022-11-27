package kopaczewski.szymczyk.glazer.estate.portal.database.services;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Announcement;
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

    public Optional<Announcement> createNewAnnouncement(
            String title, String additionalDescription, String personLogin,
            String city, String street, Integer apartmentNumber,
            Integer costPerMonth, Integer rent, Integer deposit,
            Integer roomNumber, Double area) {
        var person = personService.getPersonByLogin(personLogin);
        return person.map(value -> announcementRepository.save(
                new Announcement(
                        0L,
                        title, additionalDescription, value.getLogin(),
                        apartmentNumber, street, city,
                        costPerMonth, rent, deposit,
                        roomNumber, area
                )
        ));
    }

    public Optional<Announcement> createNewAnnouncement(Announcement announcement) {
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
            String city, String street, Integer apartmentNumber,
            Integer limit, Integer offset) {
        return announcementRepository.findFiltered(
                minCost, maxCost,
                minArea, maxArea,
                roomNumber,
                city, street, apartmentNumber,
                limit, offset);

    }

    public void updateAnnouncement(Announcement announcement) throws Exception {
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
                announcement.getApartmentNumber(),
                announcement.getStreet(),
                announcement.getCity(),
                announcement.getCostPerMonth(),
                announcement.getRent(),
                announcement.getDeposit(),
                announcement.getRoomNumber(),
                announcement.getArea());
    }

    private static boolean checkIfCorrectLoginUpdateAnnouncement(Announcement announcement, Announcement annId) {
        return !annId.getOwnerLogin().equals(announcement.getOwnerLogin());
    }

    public void removeAnnouncement(Long announcementId) {
        announcementRepository.delete(announcementRepository.getReferenceById(announcementId));
    }

    public void removeAnnouncements(List<Announcement> announcementList) {
        announcementRepository.deleteAll(announcementList);
    }
}
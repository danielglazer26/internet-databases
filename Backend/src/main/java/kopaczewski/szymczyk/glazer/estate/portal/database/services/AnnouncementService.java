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
            String title, String additionalDescription, Long personId,
            String city, String street, Integer apartmentNumber,
            Integer costPerMonth, Integer rent, Integer deposit,
            Integer roomNumber, Double area) {
        var person = personService.getPersonById(personId);
        return person.map(value -> announcementRepository.save(
                new Announcement(
                        0L,
                        title, additionalDescription, value.getPersonId(),
                        apartmentNumber, street, city,
                        costPerMonth, rent, deposit,
                        roomNumber, area
                )
        ));
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

    public void removeAnnouncement(Announcement announcement) {
        announcementRepository.delete(announcement);
    }

    public void removeAnnouncements(List<Announcement> announcementList) {
        announcementRepository.deleteAll(announcementList);
    }
}
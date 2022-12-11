package kopaczewski.szymczyk.glazer.estate.portal.database.services;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Person;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.announcement.Announcement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllModelsService {
    private final AnnouncementService announcementService;

    private final PersonService personService;

    private final PhotoService photoService;

    public AllModelsService(AnnouncementService announcementService, PersonService personService, PhotoService photoService) {
        this.announcementService = announcementService;
        this.personService = personService;
        this.photoService = photoService;
    }


    public void cascadeDelete(Person person) {

        List<Announcement> announcementsByOwnerLogin = announcementService.getAnnouncementsByOwnerLogin(person.getLogin());
        for (Announcement announcement : announcementsByOwnerLogin) {
            var photos = photoService.getPhotosByAnnouncementId(announcement.getAnnouncementId());
            photoService.removePhotos(photos);
        }
        announcementService.removeAnnouncements(announcementsByOwnerLogin);

        personService.removePerson(person);
    }
}

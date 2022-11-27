package kopaczewski.szymczyk.glazer.estate.portal.database.services;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Announcement;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PhotoServiceTest {
    @Autowired
    PhotoService service;
    @Autowired
    AnnouncementService announcementService;
    @Autowired
    PersonService personService;
    private Announcement announcement0;
    private Person person1;


    @Before
    public void setUp() {
        person1 = personService.createNewPerson("announcementTest1", "pas", "mailAnno1@niew4art0.com").get();
        announcement0 = announcementService.createNewAnnouncement(
                "test2", "des2", person1.getLogin(), "Wroclaw",
                "staro miejska", 0, 100000, 12, 12, 1000, 100.0).get();
    }

    @After
    public void tearDown() {
        personService.removePerson(person1);
        announcementService.removeAnnouncement(announcement0.getAnnouncementId());
    }

    @Test
    public void createPhoto_whenPhotoIsAddedToBase_ShouldBeSavedInDatabase() {
        try {
            File file = new File("pobrane.png");
            var photoBytes = Files.readAllBytes(file.toPath());
            var savedPhoto = service.createPhoto(photoBytes, announcement0.getAnnouncementId());
            var databasePhoto = service.findAll().stream().filter(photo -> photo.getPhotoId().equals(savedPhoto.get().getPhotoId())).findFirst();
            Assertions.assertEquals(savedPhoto, databasePhoto);
            service.removePhoto(databasePhoto.get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
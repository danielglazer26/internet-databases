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

import javax.transaction.Transactional;
import java.util.HashSet;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AnnouncementServiceTest {
    @Autowired
    AnnouncementService service;
    @Autowired
    PersonService personService;
    Person person1;
    Announcement announcement0, announcement1;

    @Before
    public void setUp() {
        person1 = personService.createNewPerson("announcementTest1", "pas", "mailAnno1@niew4art0.com").get();
        announcement0 = new Announcement(0L, "test2", "des2", person1.getPersonId(), 0,
                "staro miejska", "Wroclaw", 100000, 12, 12, 1000, 100.0, new HashSet<>());
        announcement1 = new Announcement(0L, "test1", "des1", person1.getPersonId(), 10,
                "staro miejska", "Wroclaw", 10000, 1, 1, 100, 10.0, new HashSet<>());
    }

    @After
    public void tearDown() {
        service.removeAnnouncement(announcement0);
        service.removeAnnouncement(announcement1);
        personService.removePerson(person1);
    }

    @Test
    public void createNewAnnouncement_WhenDataIsCorrect_ShouldReturnAnnouncement() {
        var optionalAnnouncement = service.createNewAnnouncement(
                announcement1.getTitle(),
                announcement1.getAdditionalDescription(),
                announcement1.getOwnerId(),
                announcement1.getCity(),
                announcement1.getStreet(),
                announcement1.getApartmentNumber(),
                announcement1.getCostPerMonth(),
                announcement1.getRent(),
                announcement1.getDeposit(),
                announcement1.getRoomNumber(),
                announcement1.getArea(),
                announcement1.getPhotos());
        var filteredAnnouncement = service.getAll().stream()
                .filter(announcement -> announcement.getTitle().equals(announcement1.getTitle())).findFirst();


        if (filteredAnnouncement.isPresent()) {
            Assertions.assertEquals(optionalAnnouncement.get(), filteredAnnouncement.get());
        } else {
            Assertions.fail();
        }
    }

    @Test
    public void getAnnouncement_WhenAreSomeAnnouncementsInDatabase_ShouldReturnGivenNumberOffAnnouncements() {
    }

    @Test
    public void getFiltered_WhenAllRequirementsAreNull_ShouldReturnAnnouncement() {
        //noinspection unused
        var announcementOptional = service.createNewAnnouncement(
                announcement0.getTitle(), announcement0.getAdditionalDescription(), announcement0.getOwnerId(),
                announcement0.getCity(), announcement0.getStreet(), announcement0.getApartmentNumber(),
                announcement0.getCostPerMonth(), announcement0.getRent(), announcement0.getDeposit(), announcement0.getRoomNumber(),
                announcement0.getArea(), announcement0.getPhotos());

        Assertions.assertEquals(1, service.getFiltered(null, null, null, null, null,
                null, null, null, 10, 0).size());
    }

    @Test
    public void getFiltered_WhenIsAnnouncementSatisfyingRequirements_ShouldReturnAnnouncement() {
        //noinspection unused
        var announcementOptional = service.createNewAnnouncement(
                announcement0.getTitle(), announcement0.getAdditionalDescription(), announcement0.getOwnerId(),
                announcement0.getCity(), announcement0.getStreet(), announcement0.getApartmentNumber(),
                announcement0.getCostPerMonth(), announcement0.getRent(), announcement0.getDeposit(), announcement0.getRoomNumber(),
                announcement0.getArea(), announcement0.getPhotos());
        Assertions.assertEquals(1, service.getFiltered(1000, 200000, 10.0, 200.0, 1000,
                "Wroclaw", "staro miejska", 0, 10, 0).size());
    }
}